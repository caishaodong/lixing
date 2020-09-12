package com.shaoxing.lixing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.IndexStatisticsDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.entity.MOrderInfo;
import com.shaoxing.lixing.domain.vo.CustomerInfoExportVO;
import com.shaoxing.lixing.domain.vo.IndexDataVo;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.LocalDateTimeUtil;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import com.shaoxing.lixing.service.MOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author caishaodong
 * @Date 2020-09-11 15:11
 * @Description 首页
 **/
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
    @Autowired
    private MOrderInfoService orderInfoService;
    @Autowired
    private MDistributionCompanyService distributionCompanyService;

    /**
     * 首页数据统计
     *
     * @return
     */
    @GetMapping("/")
    public ResponseResult<IndexDataVo> index() {
        // 今日
        Long today = LocalDateTimeUtil.getLongToday();
        // 昨日
        Long yesterday = LocalDateTimeUtil.getLongYesterday();
        // 本周一
        Long thisMonday = LocalDateTimeUtil.getLongThisMonday();
        // 上周一
        Long lastMonday = LocalDateTimeUtil.getLongLastMonday();
        // 获取今日的出货量
        QueryWrapper<MOrderInfo> todayNumQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(num,0)),0) AS todayNum");
        Map<String, Object> todayNumMap = orderInfoService.getMap(todayNumQueryWrapper);
        BigDecimal todayNum = new BigDecimal(String.valueOf(todayNumMap.get("todayNum")));

        // 获取昨日的出货量
        QueryWrapper<MOrderInfo> yesterdayNumQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", yesterday)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(num,0)),0) AS yesterdayNum");
        Map<String, Object> yesterdayNumMap = orderInfoService.getMap(yesterdayNumQueryWrapper);
        BigDecimal yesterdayNum = new BigDecimal(String.valueOf(yesterdayNumMap.get("yesterdayNum")));

        // 获取今日的营业额
        QueryWrapper<MOrderInfo> todayAmountQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS todayAmount");
        Map<String, Object> todayAmountMap = orderInfoService.getMap(todayAmountQueryWrapper);
        BigDecimal todayAmount = new BigDecimal(String.valueOf(todayAmountMap.get("todayAmount")));

        // 获取昨日的营业额
        QueryWrapper<MOrderInfo> yesterdayAmountQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", yesterday)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS yesterdayAmount");
        Map<String, Object> yesterdayAmountMap = orderInfoService.getMap(yesterdayAmountQueryWrapper);
        BigDecimal yesterdayAmount = new BigDecimal(String.valueOf(yesterdayAmountMap.get("yesterdayAmount")));

        // 获取本周营业额
        QueryWrapper<MOrderInfo> thisWeekAmountQueryWrapper = new QueryWrapper<MOrderInfo>().ge("order_date", thisMonday)
                .le("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS thisWeekAmount");
        Map<String, Object> thisWeekAmountMap = orderInfoService.getMap(thisWeekAmountQueryWrapper);
        BigDecimal thisWeekAmount = new BigDecimal(String.valueOf(thisWeekAmountMap.get("thisWeekAmount")));

        // 获取上周营业额
        QueryWrapper<MOrderInfo> lastWeekAmountQueryWrapper = new QueryWrapper<MOrderInfo>().ge("order_date", lastMonday)
                .lt("order_date", thisMonday)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS lastWeekAmount");
        Map<String, Object> lastWeekAmountMap = orderInfoService.getMap(lastWeekAmountQueryWrapper);
        BigDecimal lastWeekAmount = new BigDecimal(String.valueOf(lastWeekAmountMap.get("lastWeekAmount")));

        IndexDataVo indexDataVo = new IndexDataVo();
        indexDataVo.setTodayNum(todayNum);
        indexDataVo.setYesterdayNum(yesterdayNum);
        indexDataVo.setTodayAmount(todayAmount);
        indexDataVo.setYesterdayAmount(yesterdayAmount);
        indexDataVo.setThisWeekAmount(thisWeekAmount);
        indexDataVo.setLastWeekAmount(lastWeekAmount);
        return success(indexDataVo);
    }

    /**
     * 首页销售统计（回参：总价totalAmount）
     *
     * @param dto
     * @return
     */
    @PostMapping("/statistics")
    public ResponseResult<PageUtil<MOrderInfo>> statistics(@RequestBody IndexStatisticsDTO dto) {
        List<Long> customerIdList = StringUtil.jsonArrayToLongList(dto.getCustomerIds());
        List<Long> varietiesPriceIdList = StringUtil.jsonArrayToLongList(dto.getVarietiesPriceIds());

        IPage<MOrderInfo> page = orderInfoService.page(dto, new LambdaQueryWrapper<MOrderInfo>()
                .eq(Objects.nonNull(dto.getOrderDate()), MOrderInfo::getOrderDate, dto.getOrderDate())
                .in(!CollectionUtils.isEmpty(customerIdList), MOrderInfo::getCustomerId, customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), MOrderInfo::getVarietiesPriceId, varietiesPriceIdList)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue()));

        // 获取总价
        QueryWrapper<MOrderInfo> totalAmountQueryWrapper = new QueryWrapper<MOrderInfo>()
                .eq(Objects.nonNull(dto.getOrderDate()), "order_date", dto.getOrderDate())
                .in(!CollectionUtils.isEmpty(customerIdList), "customer_id", customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), "varieties_price_id", varietiesPriceIdList)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS totalAmount");
        Map<String, Object> totalAmountMap = orderInfoService.getMap(totalAmountQueryWrapper);
        BigDecimal totalAmount = new BigDecimal(String.valueOf(totalAmountMap.get("totalAmount")));
        dto.setTotalAmount(totalAmount);
        return success(page);
    }

    /**
     * 导出销售统计
     *
     * @param dto
     * @param response
     * @return
     */
    @GetMapping("/statistics/export")
    public ResponseResult statisticsExport(IndexStatisticsDTO dto, HttpServletResponse response) {
        List<Long> customerIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getCustomerIds());
        List<Long> varietiesPriceIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getVarietiesPriceIds());

        List<MOrderInfo> list = orderInfoService.list(new LambdaQueryWrapper<MOrderInfo>()
                .eq(Objects.nonNull(dto.getOrderDate()), MOrderInfo::getOrderDate, dto.getOrderDate())
                .in(!CollectionUtils.isEmpty(customerIdList), MOrderInfo::getCustomerId, customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), MOrderInfo::getVarietiesPriceId, varietiesPriceIdList)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));

        LinkedHashMap<String, String> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("配送单位", "distributionCompanyName");
        fieldNameMap.put("客户", "customerName");
        fieldNameMap.put("品种", "varietiesName");
        fieldNameMap.put("单位", "unit");
        fieldNameMap.put("数量", "num");
        fieldNameMap.put("单价(元)", "price");
        fieldNameMap.put("金额(元)", "totalPrice");

        try {
            LOGGER.info("开始准备导出销售统计");
            ExcelDataUtil.export(fieldNameMap, list, "销售统计", response);
        } catch (Exception e) {
            LOGGER.error("销售统计导出失败", e);
            return error();
        }
        return success();
    }

    /**
     * 导出配送清单
     *
     * @param distributionCompanyId
     * @param response
     * @return
     */
    @GetMapping("/distributionCompany/export/{distributionCompanyId}")
    public ResponseResult distributionCompanyExport(@PathVariable("distributionCompanyId") Long distributionCompanyId, HttpServletResponse response) {
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(distributionCompanyId);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        List<MOrderInfo> list = orderInfoService.list(new LambdaQueryWrapper<MOrderInfo>()
                .eq(MOrderInfo::getDistributionCompanyId, distributionCompanyId)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));

        Map<String, CustomerInfoExportVO> map = new HashMap<>();
        for (MOrderInfo orderInfo : list) {
            Long customerId = orderInfo.getCustomerId();
            String customerName = orderInfo.getCustomerName();
            String varietiesName = orderInfo.getVarietiesName();
            BigDecimal num = orderInfo.getNum();
            String unit = orderInfo.getUnit();
            String remark = orderInfo.getRemark();
            if (!map.containsKey(String.valueOf(customerId))) {
                CustomerInfoExportVO customerInfoExportVO = new CustomerInfoExportVO();
                customerInfoExportVO.setCustomerId(customerId);
                customerInfoExportVO.setCustomerName(customerName);
                customerInfoExportVO.setDetail(StringUtil.concatString(varietiesName, num.toString(), unit, remark));
                map.put(String.valueOf(customerId), customerInfoExportVO);
            } else {
                CustomerInfoExportVO customerInfoExportVO = map.get(String.valueOf(customerId));
                String detail = customerInfoExportVO.getDetail();
                customerInfoExportVO.setDetail(StringUtil.concatString(detail, "、", varietiesName, num.toString(), unit, remark));
            }
        }
        List<CustomerInfoExportVO> customerInfoExportVOList = new ArrayList<>(map.values());
        LinkedHashMap<String, String> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("客户名称", "customerName");
        fieldNameMap.put("配送明细", "detail");
        try {
            LOGGER.info("开始准备导出配送清单");
            ExcelDataUtil.export(fieldNameMap, customerInfoExportVOList, "配送清单", response);
        } catch (Exception e) {
            LOGGER.error("配送清单导出失败", e);
            return error();
        }
        return success();
    }
}
