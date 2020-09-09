package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.MCustomerPriceCategoryRel;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.MCustomerPriceCategoryRelMapper;
import com.shaoxing.lixing.service.MCustomerPriceCategoryRelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户和价目关联表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MCustomerPriceCategoryRelServiceImpl extends ServiceImpl<MCustomerPriceCategoryRelMapper, MCustomerPriceCategoryRel> implements MCustomerPriceCategoryRelService {

    @Override
    public List<MCustomerPriceCategoryRel> getListByCustomerId(Long customerId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MCustomerPriceCategoryRel>().eq(MCustomerPriceCategoryRel::getCustomerId, customerId)
                .eq(MCustomerPriceCategoryRel::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
