package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.PriceCategoryDTO;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.MPriceCategoryMapper;
import com.shaoxing.lixing.service.MPriceCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 获取价目列表（分页）
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<MPriceCategory> getListPage(PriceCategoryDTO dto) {
        return this.baseMapper.selectPage(dto, new LambdaQueryWrapper<MPriceCategory>()
                .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 根据价目名称获取价目
     *
     * @param priceCategoryName
     * @return
     */
    @Override
    public MPriceCategory getByName(String priceCategoryName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MPriceCategory>().eq(MPriceCategory::getName, priceCategoryName)
                .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取价目列表（不分页）
     *
     * @return
     */
    @Override
    public List<MPriceCategory> getList() {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MPriceCategory>()
                .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
