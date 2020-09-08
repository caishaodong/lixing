package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.service.MCustomerInfoService;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * 客户管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/customerInfo")
public class MCustomerInfoController extends BaseController {
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MDistributionCompanyService distributionCompanyService;


    /**
     * 添加客户，并绑定配送公司
     *
     * @param customerInfoDTO
     * @return
     */
    @PostMapping("/saveAndBindDistribution")
    public ResponseResult saveAndBindDistribution(@RequestBody CustomerInfoDTO customerInfoDTO) {
        if (StringUtil.isBlank(customerInfoDTO.getName())) {
            return error(BusinessEnum.CUSTOMER_NAME_EMPTY);
        }
        if (Objects.isNull(customerInfoDTO.getDistributionCompanyId())) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        // 校验客户名称是否重复
        int count = customerInfoService.count(new LambdaQueryWrapper<MCustomerInfo>().eq(MCustomerInfo::getName, customerInfoDTO.getName()));
        if (count > 0) {
            return error(BusinessEnum.CUSTOMER_NAME_REPEAT);
        }

        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(customerInfoDTO.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        customerInfoService.saveAndBindDistributionCompany(customerInfoDTO);
        return success();
    }

    /**
     * 解绑客户和配送公司关系
     *
     * @param customerInfoDTO
     * @return
     */
    @PostMapping("/unBindDistribution")
    public ResponseResult unBindDistribution(@RequestBody CustomerInfoDTO customerInfoDTO) {
        if (Objects.isNull(customerInfoDTO.getCustomerId()) || Objects.isNull(customerInfoDTO.getDistributionCompanyId())) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(customerInfoDTO.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }
        // 校验客户是否存在
        MCustomerInfo customerInfo = customerInfoService.getOKById(customerInfoDTO.getCustomerId());
        if (Objects.isNull(customerInfo)) {
            return error(BusinessEnum.CUSTOMER_NOT_EXIST);
        }
        customerInfoService.unBindDistribution(customerInfoDTO);
        return success();
    }
}
