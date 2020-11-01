package com.shaoxing.lixing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.IndexStatisticsDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.entity.MOrderInfo;
import com.shaoxing.lixing.domain.vo.CustomerInfoExportVO;
import com.shaoxing.lixing.domain.vo.IndexDataVo;
import com.shaoxing.lixing.domain.vo.IndexStatisticsVO;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.LocalDateTimeUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.global.util.business.BusinessUtil;
import com.shaoxing.lixing.global.util.decimal.DecimalUtil;
import com.shaoxing.lixing.global.util.excel.ExcelDataDTO;
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import com.shaoxing.lixing.service.MOrderInfoService;
import org.springframework.beans.BeanUtils;
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
        // 获取今日的出货量（斤）
        QueryWrapper<MOrderInfo> todayNumQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(num,0)),0) AS todayNum");
        Map<String, Object> todayNumMap = orderInfoService.getMap(todayNumQueryWrapper);
        BigDecimal todayNum = new BigDecimal(String.valueOf(todayNumMap.get("todayNum")));

        // 获取昨日的出货量（斤）
        QueryWrapper<MOrderInfo> yesterdayNumQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", yesterday)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(num,0)),0) AS yesterdayNum");
        Map<String, Object> yesterdayNumMap = orderInfoService.getMap(yesterdayNumQueryWrapper);
        BigDecimal yesterdayNum = new BigDecimal(String.valueOf(yesterdayNumMap.get("yesterdayNum")));

        // 获取今日的营业额（元）
        QueryWrapper<MOrderInfo> todayAmountQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS todayAmount");
        Map<String, Object> todayAmountMap = orderInfoService.getMap(todayAmountQueryWrapper);
        BigDecimal todayAmount = new BigDecimal(String.valueOf(todayAmountMap.get("todayAmount")));

        // 获取昨日的营业额（元）
        QueryWrapper<MOrderInfo> yesterdayAmountQueryWrapper = new QueryWrapper<MOrderInfo>().eq("order_date", yesterday)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS yesterdayAmount");
        Map<String, Object> yesterdayAmountMap = orderInfoService.getMap(yesterdayAmountQueryWrapper);
        BigDecimal yesterdayAmount = new BigDecimal(String.valueOf(yesterdayAmountMap.get("yesterdayAmount")));

        // 获取本周营业额（万元）
        QueryWrapper<MOrderInfo> thisWeekAmountQueryWrapper = new QueryWrapper<MOrderInfo>().ge("order_date", thisMonday)
                .le("order_date", today)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS thisWeekAmount");
        Map<String, Object> thisWeekAmountMap = orderInfoService.getMap(thisWeekAmountQueryWrapper);
        BigDecimal thisWeekAmount = new BigDecimal(String.valueOf(thisWeekAmountMap.get("thisWeekAmount")));

        // 获取上周营业额（万元）
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
        indexDataVo.setThisWeekAmount(DecimalUtil.divide(thisWeekAmount, new BigDecimal("10000")));
        indexDataVo.setLastWeekAmount(DecimalUtil.divide(lastWeekAmount, new BigDecimal("10000")));
        return success(indexDataVo);
    }

    /**
     * 首页销售统计
     *
     * @param dto
     * @return
     */
    @PostMapping("/statistics")
    public ResponseResult<IndexStatisticsVO<MOrderInfo>> statistics(@RequestBody IndexStatisticsDTO dto) {
        List<Long> distributionCompanyIdList = StringUtil.jsonArrayToLongList(dto.getDistributionCompanyIds());
        List<Long> customerIdList = StringUtil.jsonArrayToLongList(dto.getCustomerIds());
        List<Long> varietiesPriceIdList = StringUtil.jsonArrayToLongList(dto.getVarietiesPriceIds());

        IPage<MOrderInfo> page = orderInfoService.page(dto, new LambdaQueryWrapper<MOrderInfo>()
                .ge(Objects.nonNull(dto.getStartOrderDate()), MOrderInfo::getOrderDate, dto.getStartOrderDate())
                .le(Objects.nonNull(dto.getEndOrderDate()), MOrderInfo::getOrderDate, dto.getEndOrderDate())
                .in(!CollectionUtils.isEmpty(distributionCompanyIdList), MOrderInfo::getDistributionCompanyId, distributionCompanyIdList)
                .in(!CollectionUtils.isEmpty(customerIdList), MOrderInfo::getCustomerId, customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), MOrderInfo::getVarietiesPriceId, varietiesPriceIdList)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue()));

        // 获取总价
        QueryWrapper<MOrderInfo> totalAmountQueryWrapper = new QueryWrapper<MOrderInfo>()
                .ge(Objects.nonNull(dto.getStartOrderDate()), "order_date", dto.getStartOrderDate())
                .le(Objects.nonNull(dto.getEndOrderDate()), "order_date", dto.getEndOrderDate())
                .in(!CollectionUtils.isEmpty(distributionCompanyIdList), "distribution_company_id", distributionCompanyIdList)
                .in(!CollectionUtils.isEmpty(customerIdList), "customer_id", customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), "varieties_price_id", varietiesPriceIdList)
                .eq("is_deleted", YesNoEnum.NO.getValue())
                .select("IFNULL(sum(IFNULL(total_price,0)),0) AS totalAmount, IFNULL(SUM(IFNULL(num, 0)),0) AS totalNum");
        Map<String, Object> totalMap = orderInfoService.getMap(totalAmountQueryWrapper);

        // 封装返回值
        IndexStatisticsVO indexStatisticsVO = new IndexStatisticsVO();
        BeanUtils.copyProperties(page, indexStatisticsVO);
        indexStatisticsVO.setTotalAmount(new BigDecimal(String.valueOf(totalMap.get("totalAmount"))));
        indexStatisticsVO.setTotalNum(new BigDecimal(String.valueOf(totalMap.get("totalNum"))));
        return success(indexStatisticsVO);
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
        List<Long> distributionCompanyIdList = StringUtil.jsonArrayToLongList(dto.getDistributionCompanyIds());
        List<Long> customerIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getCustomerIds());
        List<Long> varietiesPriceIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getVarietiesPriceIds());

        List<MOrderInfo> list = orderInfoService.list(new LambdaQueryWrapper<MOrderInfo>()
                .ge(Objects.nonNull(dto.getStartOrderDate()), MOrderInfo::getOrderDate, dto.getStartOrderDate())
                .le(Objects.nonNull(dto.getEndOrderDate()), MOrderInfo::getOrderDate, dto.getEndOrderDate())
                .in(!CollectionUtils.isEmpty(distributionCompanyIdList), MOrderInfo::getDistributionCompanyId, distributionCompanyIdList)
                .in(!CollectionUtils.isEmpty(customerIdList), MOrderInfo::getCustomerId, customerIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), MOrderInfo::getVarietiesPriceId, varietiesPriceIdList)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));

        // 标题
        String title = "绍兴市立兴农产品有限公司销售统计";
        // 获取总金额
        BigDecimal totalMoney = CollectionUtils.isEmpty(list) ? BigDecimal.ZERO : list.stream().map(MOrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        LinkedHashMap<String, String[]> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("配送单位", new String[]{"distributionCompanyName", Constant.COLUMN_WIDTH_40});
        fieldNameMap.put("客户名称", new String[]{"customerName", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("品种", new String[]{"varietiesName"});
        fieldNameMap.put("单位", new String[]{"unit"});
        fieldNameMap.put("数量", new String[]{"num"});
        fieldNameMap.put("单价(元)", new String[]{"price"});
        fieldNameMap.put("金额(元)", new String[]{"totalPrice"});

        try {
            LOGGER.info("开始准备导出销售统计");
            ExcelDataUtil.export(new ExcelDataDTO<>(title, fieldNameMap, list, "销售统计", Boolean.TRUE, BusinessUtil.getIndexStatisticsExportTailMapList(totalMoney)), response);
            LOGGER.info("销售统计导出完成");
        } catch (Exception e) {
            LOGGER.error("销售统计导出失败", e);
            return error();
        }
        return success();
    }


    /**
     * 导出配送清单
     *
     * @param distributionCompanyId 配送单位id
     * @param dto
     * @param response
     * @return
     */
    @GetMapping("/distributionCompany/export/{distributionCompanyId}")
    public ResponseResult distributionCompanyExport(@PathVariable("distributionCompanyId") Long distributionCompanyId,
                                                    IndexStatisticsDTO dto,
                                                    HttpServletResponse response) {
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(distributionCompanyId);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }

        List<Long> customerIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getCustomerIds());
        List<Long> priceCategoryIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getPriceCategoryIds());
        List<Long> varietiesPriceIdList = Objects.isNull(dto) ? new ArrayList<>() : StringUtil.jsonArrayToLongList(dto.getVarietiesPriceIds());

        List<MOrderInfo> list = orderInfoService.list(new LambdaQueryWrapper<MOrderInfo>()
                .eq(MOrderInfo::getDistributionCompanyId, distributionCompanyId)
                .eq(Objects.nonNull(dto.getOrderDate()), MOrderInfo::getOrderDate, dto.getOrderDate())
                .ge(Objects.isNull(dto.getOrderDate()) && Objects.nonNull(dto.getStartOrderDate()), MOrderInfo::getOrderDate, dto.getStartOrderDate())
                .le(Objects.isNull(dto.getOrderDate()) && Objects.nonNull(dto.getEndOrderDate()), MOrderInfo::getOrderDate, dto.getEndOrderDate())
                .in(!CollectionUtils.isEmpty(customerIdList), MOrderInfo::getCustomerId, customerIdList)
                .in(CollectionUtils.isEmpty(varietiesPriceIdList) && !CollectionUtils.isEmpty(priceCategoryIdList), MOrderInfo::getPriceCategoryId, priceCategoryIdList)
                .in(!CollectionUtils.isEmpty(varietiesPriceIdList), MOrderInfo::getVarietiesPriceId, varietiesPriceIdList)
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
                customerInfoExportVO.setDetail(StringUtil.concatString(varietiesName, DecimalUtil.formatPretty(num), unit, remark));
                map.put(String.valueOf(customerId), customerInfoExportVO);
            } else {
                CustomerInfoExportVO customerInfoExportVO = map.get(String.valueOf(customerId));
                String detail = customerInfoExportVO.getDetail();
                customerInfoExportVO.setDetail(StringUtil.concatString(detail, "、", varietiesName, num.toString(), unit, remark));
            }
        }

        String title = "绍兴市立兴农产品有限公司配送清单";
        if (Objects.nonNull(dto.getOrderDate())) {
            title = title + "(" + dto.getOrderDate() + ")";
        }

        List<CustomerInfoExportVO> customerInfoExportVOList = new ArrayList<>(map.values());
        LinkedHashMap<String, String[]> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("客户名称", new String[]{"customerName", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("配送明细", new String[]{"detail", Constant.COLUMN_WIDTH_200});
        try {
            LOGGER.info("开始准备导出配送清单");
            ExcelDataUtil.export(new ExcelDataDTO<>(title, fieldNameMap, customerInfoExportVOList, "配送清单", Boolean.TRUE, null), response);
            LOGGER.info("配送清单导出完成");
        } catch (Exception e) {
            LOGGER.error("配送清单导出失败", e);
            return error();
        }
        return success();
    }
}
