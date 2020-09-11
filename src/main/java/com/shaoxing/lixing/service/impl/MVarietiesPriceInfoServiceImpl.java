package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MVarietiesPriceInfo;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.mapper.MVarietiesPriceInfoMapper;
import com.shaoxing.lixing.service.MVarietiesPriceInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品种价格信息表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MVarietiesPriceInfoServiceImpl extends ServiceImpl<MVarietiesPriceInfoMapper, MVarietiesPriceInfo> implements MVarietiesPriceInfoService {

    @Override
    public MVarietiesPriceInfo getOkById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MVarietiesPriceInfo>().eq(MVarietiesPriceInfo::getId, id)
                .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO));
    }

    /**
     * 获取品种价格列表 分页
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<MVarietiesPriceInfo> getListPage(VarietiesPriceInfoSearchDTO dto) {
        PageUtil<MVarietiesPriceInfo> pageUtil = new PageUtil<>();
        pageUtil.setCurrent(dto.getCurrent());
        pageUtil.setSize(dto.getSize());
        IPage<MVarietiesPriceInfo> page = this.page(pageUtil, new LambdaQueryWrapper<MVarietiesPriceInfo>()
                .eq(MVarietiesPriceInfo::getPriceCategoryId, dto.getPriceCategoryId())
                .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(MVarietiesPriceInfo::getGmtCreate));
        return page;
    }

    /**
     * 获取品种价格列表 不分页
     *
     * @param priceCategoryId
     * @return
     */
    @Override
    public List<MVarietiesPriceInfo> getListByPriceCategoryId(Long priceCategoryId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MVarietiesPriceInfo>()
                .eq(MVarietiesPriceInfo::getPriceCategoryId, priceCategoryId)
                .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
