package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.entity.MCustomerDistributionCompanyRel;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.domain.entity.MCustomerPriceCategoryRel;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.mapper.MCustomerInfoMapper;
import com.shaoxing.lixing.service.MCustomerDistributionCompanyRelService;
import com.shaoxing.lixing.service.MCustomerInfoService;
import com.shaoxing.lixing.service.MCustomerPriceCategoryRelService;
import com.shaoxing.lixing.service.MPriceCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MCustomerInfoServiceImpl extends ServiceImpl<MCustomerInfoMapper, MCustomerInfo> implements MCustomerInfoService {
    @Autowired
    private MCustomerDistributionCompanyRelService customerDistributionCompanyRelService;
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MPriceCategoryService priceCategoryService;
    @Autowired
    private MCustomerPriceCategoryRelService customerPriceCategoryRelService;

    @Override
    public MCustomerInfo getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MCustomerInfo>().eq(MCustomerInfo::getId, id)
                .eq(MCustomerInfo::getIsDeleted, YesNoEnum.NO));
    }

    /**
     * 添加客户，并绑定配送公司
     *
     * @param customerInfoDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndBindDistributionCompany(CustomerInfoDTO customerInfoDTO) {
        // 添加客户
        MCustomerInfo customerInfo = new MCustomerInfo();
        BeanUtils.copyProperties(customerInfoDTO, customerInfo);
        ReflectUtil.setCreateInfo(customerInfo, MCustomerInfo.class);
        this.baseMapper.insert(customerInfo);
        // 绑定配送公司
        MCustomerDistributionCompanyRel customerDistributionCompanyRel = new MCustomerDistributionCompanyRel();
        customerDistributionCompanyRel.setCustomerId(customerInfo.getId());
        customerDistributionCompanyRel.setDistributionCompanyId(customerInfoDTO.getDistributionCompanyId());
        ReflectUtil.setCreateInfo(customerDistributionCompanyRel, MCustomerDistributionCompanyRel.class);
        customerDistributionCompanyRelService.save(customerDistributionCompanyRel);
    }

    /**
     * 解绑客户和配送公司关系
     *
     * @param customerInfoDTO
     */
    @Override
    public void unBindDistribution(CustomerInfoDTO customerInfoDTO) {
        customerDistributionCompanyRelService.remove(new LambdaQueryWrapper<MCustomerDistributionCompanyRel>()
                .eq(MCustomerDistributionCompanyRel::getCustomerId, customerInfoDTO.getCustomerId())
                .eq(MCustomerDistributionCompanyRel::getDistributionCompanyId, customerInfoDTO.getDistributionCompanyId()));
    }

    /**
     * 客户绑定价目
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult customersBindingPriceCategory(CustomerBindingPriceCategoryDTO dto) {
        // 客户信息校验
        List<Long> custormerIdList = dto.getCustormerIdList();
        int count = customerInfoService.count(new LambdaQueryWrapper<MCustomerInfo>().in(MCustomerInfo::getId, custormerIdList));
        if (count != custormerIdList.size()) {
            return ResponseResult.error(BusinessEnum.CUSTOMER_INFO_ERROR);
        }
        // 价目信息校验
        MPriceCategory priceCategory = priceCategoryService.getOKById(dto.getPriceCategoryId());
        if (Objects.isNull(priceCategory)) {
            return ResponseResult.error(BusinessEnum.PRICE_CATEGORY_INFO_ERROR);
        }
        // 获取已经绑定过该价目的客户关系
        List<MCustomerPriceCategoryRel> customerPriceCategoryRelList = customerPriceCategoryRelService.list(new LambdaQueryWrapper<MCustomerPriceCategoryRel>()
                .in(MCustomerPriceCategoryRel::getCustomerId, custormerIdList)
                .eq(MCustomerPriceCategoryRel::getPriceCategoryId, priceCategory.getId())
                .eq(MCustomerPriceCategoryRel::getIsDeleted, YesNoEnum.NO.getValue()));
        List<Long> existCustomerIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(customerPriceCategoryRelList)) {
            existCustomerIds = customerPriceCategoryRelList.stream().map(MCustomerPriceCategoryRel::getCustomerId).collect(Collectors.toList());
        }

        // 客户绑定价目
        List<MCustomerPriceCategoryRel> list = new ArrayList<>();
        for (Long customerId : custormerIdList) {
            if (!existCustomerIds.contains(customerId)) {
                MCustomerPriceCategoryRel rel = new MCustomerPriceCategoryRel();
                rel.setCustomerId(customerId);
                rel.setPriceCategoryId(dto.getPriceCategoryId());
                ReflectUtil.setCreateInfo(rel, MCustomerPriceCategoryRel.class);
                list.add(rel);
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            customerPriceCategoryRelService.saveBatch(list);
        }

        return ResponseResult.success();
    }
}
