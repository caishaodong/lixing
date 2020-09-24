package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.domain.entity.MCustomerPriceCategoryRel;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.service.MCustomerInfoService;
import com.shaoxing.lixing.service.MCustomerPriceCategoryRelService;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import com.shaoxing.lixing.service.MPriceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/customerInfo")
public class MCustomerInfoController extends BaseController {
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MDistributionCompanyService distributionCompanyService;
    @Autowired
    private MCustomerPriceCategoryRelService customerPriceCategoryRelService;
    @Autowired
    private MPriceCategoryService priceCategoryService;


    /**
     * 添加客户，并绑定配送公司
     *
     * @param dto
     * @return
     */
    @PostMapping("/saveAndBindDistribution")
    public ResponseResult<MCustomerInfo> saveAndBindDistribution(@RequestBody CustomerInfoDTO dto) {
        if (StringUtil.isBlank(dto.getName())) {
            return error(BusinessEnum.CUSTOMER_NAME_EMPTY);
        }
        if (Objects.isNull(dto.getDistributionCompanyId())) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        // 校验客户名称是否重复
        int count = customerInfoService.count(new LambdaQueryWrapper<MCustomerInfo>().eq(MCustomerInfo::getName, dto.getName()));
        if (count > 0) {
            return error(BusinessEnum.CUSTOMER_NAME_REPEAT);
        }

        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(dto.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        MCustomerInfo customerInfo = customerInfoService.saveAndBindDistributionCompany(dto);
        return success(customerInfo);
    }

    /**
     * 解绑客户和配送公司关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/unBindDistribution")
    public ResponseResult unBindDistribution(@RequestBody CustomerInfoDTO dto) {
        if (Objects.isNull(dto.getCustomerId()) || Objects.isNull(dto.getDistributionCompanyId())) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(dto.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        // 校验客户是否存在
        MCustomerInfo customerInfo = customerInfoService.getOKById(dto.getCustomerId());
        if (Objects.isNull(customerInfo)) {
            return error(BusinessEnum.CUSTOMER_NOT_EXIST);
        }
        customerInfoService.unBindDistribution(dto);
        return success();
    }

    /**
     * 根据配送公司id获取绑定的客户列表
     *
     * @param distributionCompanyId 配送公司id
     * @return
     */
    @GetMapping("/getCustomerListByDistributionCompanyId/{distributionCompanyId}")
    public ResponseResult<List<MCustomerInfo>> getCustomerListByDistributionCompanyId(@PathVariable("distributionCompanyId") Long distributionCompanyId) {
        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(distributionCompanyId);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        List<MCustomerInfo> list = customerInfoService.getCustomerListByDistributionCompanyId(distributionCompanyId);
        return success(list);
    }

    /**
     * 客户绑定价目
     *
     * @param dto
     * @return
     */
    @PostMapping("/customersBindingPriceCategory")
    public ResponseResult customersBindingPriceCategory(@RequestBody CustomerBindingPriceCategoryDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        ResponseResult responseResult = customerInfoService.customersBindingPriceCategory(dto);
        return responseResult;
    }

    /**
     * 根据客户id获取价目列表
     *
     * @param customerId 客户id
     * @return
     */
    @GetMapping("/getPriceCategoryByCustomerId/{customerId}")
    public ResponseResult<List<MPriceCategory>> getPriceCategoryByCustomerId(@PathVariable("customerId") Long customerId) {
        List<MPriceCategory> list = new ArrayList<>();
        // 校验客户是否存在
        MCustomerInfo customerInfo = customerInfoService.getOKById(customerId);
        if (Objects.isNull(customerInfo)) {
            return error(BusinessEnum.CUSTOMER_NOT_EXIST);
        }
        // 根据客户id获取绑定的价目列表
        List<MCustomerPriceCategoryRel> customerPriceCategoryRelList = customerPriceCategoryRelService.getListByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(customerPriceCategoryRelList)) {
            List<Long> priceCategoryIdList = customerPriceCategoryRelList.stream().map(MCustomerPriceCategoryRel::getPriceCategoryId).collect(Collectors.toList());
            list = priceCategoryService.list(new LambdaQueryWrapper<MPriceCategory>().in(MPriceCategory::getId, priceCategoryIdList)
                    .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
        }
        return success(list);
    }

    /**
     * 获取全部客户列表（不分页）
     *
     * @return
     */
    @GetMapping("/getList")
    public ResponseResult<List<MCustomerInfo>> getList() {
        List<MCustomerInfo> list = customerInfoService.getList();
        return success(list);
    }

    /**
     * 根据价目id获取绑定的客户信息
     *
     * @param priceCategoryId 价目id
     * @return
     */
    @GetMapping("/getCustomerInfoList")
    public ResponseResult<List<MCustomerInfo>> getCustomerInfoList(@RequestParam(value = "priceCategoryId") Long priceCategoryId) {
        List<MCustomerInfo> list = customerInfoService.getCustomerInfoList(priceCategoryId);
        return success(list);
    }
}
