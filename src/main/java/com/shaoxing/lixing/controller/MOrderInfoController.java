package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.OrderInfoDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoExportDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.domain.vo.OrderInfoSearchVO;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.OrderSourceEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.OrderNoUtils;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.decimal.DecimalUtil;
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.global.util.excel.ExcelSheetPO;
import com.shaoxing.lixing.global.util.excel.ExcelUtil;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 订单管理
 *
 * @author caishaodong
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/orderInfo")
public class MOrderInfoController extends BaseController {

    @Autowired
    private MDistributionCompanyService distributionCompanyService;
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MVarietiesPriceInfoService varietiesPriceInfoService;
    @Autowired
    private MPriceCategoryService priceCategoryService;
    @Autowired
    private MOrderInfoService orderInfoService;
    @Autowired
    private MOrderCheckDetailService orderCheckDetailService;

    /**
     * 获取订单列表（分页）
     *
     * @param dto
     * @return
     */
    @PostMapping("/getListPage")
    public ResponseResult<OrderInfoSearchVO<MOrderInfo>> getListPage(@RequestBody OrderInfoSearchDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        IPage<MOrderInfo> page = orderInfoService.getListPage(dto);

        // 获取标题
        String title = orderCheckDetailService.getTitle(dto.getOrderDate(), dto.getDistributionCompanyId());

        // 封装返回值
        OrderInfoSearchVO orderInfoSearchVO = new OrderInfoSearchVO();
        BeanUtils.copyProperties(page, orderInfoSearchVO);
        orderInfoSearchVO.setTitle(title);
        return success(page);
    }

    /**
     * 创建（修改）订单
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody OrderInfoDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        MOrderInfo orderInfo = new MOrderInfo();
        if (Objects.isNull(dto.getId())) {
            // 新增
            ReflectUtil.setCreateInfo(orderInfo, MOrderInfo.class);
        } else {
            // 修改
            orderInfo = orderInfoService.getOKById(dto.getId());
            if (Objects.isNull(orderInfo)) {
                return error(BusinessEnum.ORDER_NOT_EXIST);
            }
            orderInfo.setGmtModified(LocalDateTime.now());
        }
        BeanUtils.copyProperties(dto, orderInfo);


        // 校验配送单位是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(orderInfo.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        orderInfo.setDistributionCompanyName(distributionCompany.getName());
        // 校验客户是否存在
        MCustomerInfo customerInfo = customerInfoService.getOKById(dto.getCustomerId());
        if (Objects.isNull(customerInfo)) {
            return error(BusinessEnum.CUSTOMER_NOT_EXIST);
        }
        orderInfo.setCustomerName(customerInfo.getName());
        // 校验品种价格是否存在
        MVarietiesPriceInfo varietiesPriceInfo = varietiesPriceInfoService.getOkById(dto.getVarietiesPriceId());
        if (Objects.isNull(varietiesPriceInfo)) {
            return error(BusinessEnum.VARIETIES_PRICE_NOT_EXIST);
        }
        orderInfo.setVarietiesName(varietiesPriceInfo.getName());
        // 获取价目信息
        MPriceCategory priceCategory = priceCategoryService.getOKById(varietiesPriceInfo.getPriceCategoryId());
        if (Objects.isNull(priceCategory)) {
            return error(BusinessEnum.PRICE_CATEGORY_NOT_EXIST);
        }
        orderInfo.setPriceCategoryId(priceCategory.getId());
        orderInfo.setPriceCategoryName(priceCategory.getName());
        // 设置单价
        orderInfo.setPrice(varietiesPriceInfo.getPrice());
        // 设置单位
        orderInfo.setUnit(varietiesPriceInfo.getUnit());
        // 计算总价
        orderInfo.setTotalPrice(DecimalUtil.multiply(varietiesPriceInfo.getPrice(), orderInfo.getNum()));

        if (Objects.isNull(dto.getId())) {
            // 生成订单编号
            orderInfo.setOrderSn(OrderNoUtils.getSerialNumber());
            orderInfo.setSource(OrderSourceEnum.ADMIN.getSource());
            orderInfoService.save(orderInfo);
        } else {
            // 修改
            orderInfoService.updateById(orderInfo);
        }

        return success();
    }

    /**
     * 复制订单
     *
     * @param id 订单id
     * @return
     */
    @PostMapping("/reproduce/{id}")
    public ResponseResult reproduce(@PathVariable("id") Long id) {
        MOrderInfo existOrderId = orderInfoService.getOKById(id);
        if (Objects.isNull(existOrderId)) {
            return error(BusinessEnum.ORDER_NOT_EXIST);
        }
        MOrderInfo newOrderInfo = new MOrderInfo();
        BeanUtils.copyProperties(existOrderId, newOrderInfo, "id");
        ReflectUtil.setCreateInfo(newOrderInfo, MOrderInfo.class);
        // 生成订单编号
        newOrderInfo.setOrderSn(OrderNoUtils.getSerialNumber());
        newOrderInfo.setSource(OrderSourceEnum.COPY.getSource());
        orderInfoService.save(newOrderInfo);
        return success();
    }

    /**
     * 删除订单
     *
     * @param id 订单id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        MOrderInfo existOrderId = orderInfoService.getOKById(id);
        if (Objects.isNull(existOrderId)) {
            return success();
        }
        existOrderId.setIsDeleted(YesNoEnum.YES.getValue());
        existOrderId.setGmtModified(LocalDateTime.now());
        orderInfoService.updateById(existOrderId);
        return success();
    }

    /**
     * 导出订单
     *
     * @param dto
     * @param response
     * @return
     */
    @GetMapping("/export")
    public ResponseResult export(OrderInfoExportDTO dto, HttpServletResponse response) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        // 获取订单标题
        String title = orderCheckDetailService.getTitle(dto.getOrderDate(), dto.getDistributionCompanyId());

        // 获取需要导出的订单列表
        List<MOrderInfo> orderInfoList = orderInfoService.getList(dto);

        LinkedHashMap<String, String[]> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("订单日期", new String[]{"orderDate"});
        fieldNameMap.put("配送单位", new String[]{"distributionCompanyName", Constant.COLUMN_WIDTH_40});
        fieldNameMap.put("客户", new String[]{"customerName", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("价目", new String[]{"priceCategoryName"});
        fieldNameMap.put("品种", new String[]{"varietiesName"});
        fieldNameMap.put("单位", new String[]{"unit"});
        fieldNameMap.put("数量", new String[]{"num"});
        fieldNameMap.put("单价(元)", new String[]{"price"});
        fieldNameMap.put("总价(元)", new String[]{"totalPrice"});
        fieldNameMap.put("备注", new String[]{"remark", Constant.COLUMN_WIDTH_80});

        try {
            LOGGER.info("开始准备导出订单");
            ExcelDataUtil.export(title, fieldNameMap, orderInfoList, "订单", response);
            LOGGER.info("订单导出完成");
        } catch (Exception e) {
            LOGGER.error("订单导出失败", e);
            return error();
        }
        return success();
    }

    /**
     * 导入订单
     *
     * @param file 导入文件
     * @return
     */
    @PostMapping("/importExcel")
    public ResponseResult importExcel(@RequestParam(value = "file") MultipartFile file) {
        List<ExcelSheetPO> excelSheetPOList;
        try {
            LOGGER.info("开始导入订单");
            excelSheetPOList = ExcelUtil.readExcel(file);
        } catch (IOException e) {
            LOGGER.error("导入订单失败", e);
            return error();
        }
        List<List<Object>> dataList;
        ResponseResult errorResult = null;
        if (!CollectionUtils.isEmpty(excelSheetPOList) && !CollectionUtils.isEmpty(dataList = excelSheetPOList.get(0).getDataList()) && dataList.size() > 1) {
            List<MOrderInfo> list = new ArrayList<>();

            // 从第二行开始获取每一行的数据
            for (int i = 1; i < dataList.size(); i++) {
                List<Object> rowData = dataList.get(i);
                MOrderInfo orderInfo = new MOrderInfo();
                int j = 0;
                orderInfo.setOrderDate(Long.valueOf(String.valueOf(rowData.get(j++))));
                orderInfo.setDistributionCompanyName(String.valueOf(rowData.get(j++)));
                orderInfo.setCustomerName(String.valueOf(rowData.get(j++)));
                orderInfo.setPriceCategoryName(String.valueOf(rowData.get(j++)));
                orderInfo.setVarietiesName(String.valueOf(rowData.get(j++)));
                orderInfo.setNum(new BigDecimal(String.valueOf(rowData.get(j++))));
                orderInfo.setRemark(String.valueOf(rowData.get(j++)));
                errorResult = orderInfoService.dataCheck(orderInfo);
                if (Objects.nonNull(errorResult)) {
                    break;
                }
                orderInfo.setOrderSn(OrderNoUtils.getSerialNumber());
                orderInfo.setSource(OrderSourceEnum.IMPORT.getSource());
                list.add(orderInfo);
            }
            if (Objects.isNull(errorResult) && !CollectionUtils.isEmpty(list)) {
                orderInfoService.saveBatch(list);
            }
        }
        return Objects.nonNull(errorResult) ? errorResult : success();
    }

}
