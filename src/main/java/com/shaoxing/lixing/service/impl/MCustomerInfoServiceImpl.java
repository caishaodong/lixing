package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.entity.MCustomerDistributionCompanyRel;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.mapper.MCustomerInfoMapper;
import com.shaoxing.lixing.service.MCustomerDistributionCompanyRelService;
import com.shaoxing.lixing.service.MCustomerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
