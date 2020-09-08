package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.MOrderCheckDetailMapper;
import com.shaoxing.lixing.service.MOrderCheckDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单送货验收信息表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MOrderCheckDetailServiceImpl extends ServiceImpl<MOrderCheckDetailMapper, MOrderCheckDetail> implements MOrderCheckDetailService {

    @Override
    public MOrderCheckDetail getOKByOrderDate(Long orderDate) {
        MOrderCheckDetail orderCheckDetail = this.baseMapper.selectOne(new LambdaQueryWrapper<MOrderCheckDetail>()
                .eq(MOrderCheckDetail::getOrderDate, orderDate)
                .eq(MOrderCheckDetail::getIsDeleted, YesNoEnum.NO.getValue()));
        return orderCheckDetail;
    }
}
