package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.MPriceCategoryMapper;
import com.shaoxing.lixing.service.MPriceCategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 价目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MPriceCategoryServiceImpl extends ServiceImpl<MPriceCategoryMapper, MPriceCategory> implements MPriceCategoryService {

    @Override
    public MPriceCategory getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MPriceCategory>().eq(MPriceCategory::getId, id)
                .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO));
    }
}
