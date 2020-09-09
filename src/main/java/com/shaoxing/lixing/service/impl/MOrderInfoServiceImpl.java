package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MOrderInfo;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.mapper.MOrderInfoMapper;
import com.shaoxing.lixing.service.MOrderInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-08
 */
@Service
public class MOrderInfoServiceImpl extends ServiceImpl<MOrderInfoMapper, MOrderInfo> implements MOrderInfoService {

    @Override
    public MOrderInfo getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MOrderInfo>().eq(MOrderInfo::getId, id)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取订单列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageUtil<MOrderInfo> getListPage(OrderInfoSearchDTO dto) {
        PageUtil<MOrderInfo> pageUtil = new PageUtil<>();
        pageUtil.setCurrent(dto.getCurrent());
        pageUtil.setSize(dto.getSize());

        IPage<MOrderInfo> page = this.page(pageUtil, new LambdaQueryWrapper<MOrderInfo>().eq(MOrderInfo::getOrderDate, dto.getOrderDate())
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));
        return (PageUtil<MOrderInfo>) page;
    }
}
