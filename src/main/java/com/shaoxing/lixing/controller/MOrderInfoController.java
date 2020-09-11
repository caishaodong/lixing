package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.dto.OrderInfoDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.DecimalUtil;
import com.shaoxing.lixing.global.util.OrderNoUtils;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.excel.ExcelSheetPO;
import com.shaoxing.lixing.global.util.excel.ExcelUtil;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    /**
     * 获取订单列表（分页）
     *
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<MOrderInfo>> getListPage(@RequestBody OrderInfoSearchDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        PageUtil<MOrderInfo> page = orderInfoService.getListPage(dto);
        return success(page);
    }

    /**
     * 创建订单
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
        BeanUtils.copyProperties(dto, orderInfo);
        ReflectUtil.setCreateInfo(orderInfo, MOrderInfo.class);

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
        // 计算总价
        orderInfo.setTotalPrice(DecimalUtil.multiply(varietiesPriceInfo.getPrice(), new BigDecimal(String.valueOf(orderInfo.getNum()))));
        // 生成订单编号
        orderInfo.setOrderSn(OrderNoUtils.getSerialNumber());
        orderInfoService.save(orderInfo);
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
                orderInfo.setNum(Integer.valueOf(String.valueOf(rowData.get(j++))));
                orderInfo.setRemark(String.valueOf(rowData.get(j++)));
                errorResult = dataCheck(orderInfo);
                if (Objects.nonNull(errorResult)) {
                    break;
                }
                list.add(orderInfo);
            }
        }
        return Objects.nonNull(errorResult) ? errorResult : success();
    }

    /**
     * 订单导入信息校验
     *
     * @param orderInfo
     * @return
     */
    private ResponseResult dataCheck(MOrderInfo orderInfo) {
        // 根据配送单位名称获取配送单位id
        // 根据客户名称获取客户id
        // 根据价目名称获取价目id
        // 根据价目id和品种名称获取品种id，品种单价
        // 根据数量和单价，计算总价
        return error(BusinessEnum.CUSTOMER_INFO_ERROR);
    }

}
