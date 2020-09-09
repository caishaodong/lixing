package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.FCompanyBill;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.FCompanyBillMapper;
import com.shaoxing.lixing.service.FCompanyBillService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司账单表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class FCompanyBillServiceImpl extends ServiceImpl<FCompanyBillMapper, FCompanyBill> implements FCompanyBillService {

    @Override
    public FCompanyBill getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<FCompanyBill>().eq(FCompanyBill::getId, id)
                .eq(FCompanyBill::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
