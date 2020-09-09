package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.dto.OrderInfoDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.DecimalUtil;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
     * 获取订单列表
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
    @PostMapping("/sava")
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
        orderInfo.setPriceCategoryName(priceCategory.getName());
        // 设置单价
        orderInfo.setPrice(varietiesPriceInfo.getPrice());
        // 计算总价
        orderInfo.setTotalPrice(DecimalUtil.multiply(varietiesPriceInfo.getPrice(), new BigDecimal(String.valueOf(orderInfo.getNum()))));
        // 生成订单编号
        orderInfo.setOrderSn(StringUtil.genOrderSn());
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
        orderInfoService.updateById(existOrderId);
        return success();
    }

}
