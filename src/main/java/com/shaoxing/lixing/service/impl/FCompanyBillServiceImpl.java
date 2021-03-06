package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CompanyBillSearchDTO;
import com.shaoxing.lixing.domain.entity.FCompanyBill;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.FCompanyBillMapper;
import com.shaoxing.lixing.service.FCompanyBillService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 获取公司账单列表（分页）
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<FCompanyBill> getListPage(CompanyBillSearchDTO dto) {
        IPage<FCompanyBill> page = this.page(dto, new LambdaQueryWrapper<FCompanyBill>()
                .eq(FCompanyBill::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(FCompanyBill::getGmtCreate));
        return page;
    }

    /**
     * 获取账单列表（不分页）
     *
     * @return
     */
    @Override
    public List<FCompanyBill> getList() {
        return this.baseMapper.selectList(new LambdaQueryWrapper<FCompanyBill>()
                .eq(FCompanyBill::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(FCompanyBill::getGmtCreate));
    }
}
