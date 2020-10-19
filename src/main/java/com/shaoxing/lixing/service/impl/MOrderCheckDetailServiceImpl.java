package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.mapper.MOrderCheckDetailMapper;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import com.shaoxing.lixing.service.MOrderCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Autowired
    private MDistributionCompanyService distributionCompanyService;

    @Override
    public MOrderCheckDetail getOKByOrderDateAndDistributionCompanyId(Long orderDate, Long distributionCompanyId) {
        MOrderCheckDetail existsOrderCheckDetail = this.baseMapper.selectOne(new LambdaQueryWrapper<MOrderCheckDetail>()
                .eq(MOrderCheckDetail::getOrderDate, orderDate)
                .eq(MOrderCheckDetail::getDistributionCompanyId, distributionCompanyId)
                .eq(MOrderCheckDetail::getIsDeleted, YesNoEnum.NO.getValue()));

        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(distributionCompanyId);

        if (Objects.isNull(existsOrderCheckDetail)) {
            synchronized (MOrderCheckDetailServiceImpl.class) {
                existsOrderCheckDetail = this.baseMapper.selectOne(new LambdaQueryWrapper<MOrderCheckDetail>()
                        .eq(MOrderCheckDetail::getOrderDate, orderDate)
                        .eq(MOrderCheckDetail::getDistributionCompanyId, distributionCompanyId)
                        .eq(MOrderCheckDetail::getIsDeleted, YesNoEnum.NO.getValue()));
                if (Objects.isNull(existsOrderCheckDetail)) {
                    // 新增
                    existsOrderCheckDetail = new MOrderCheckDetail();
                    existsOrderCheckDetail.setOrderDate(orderDate);
                    existsOrderCheckDetail.setDistributionCompanyId(distributionCompanyId);
                    existsOrderCheckDetail.setTitle(distributionCompany.getName());
                    ReflectUtil.setCreateInfo(existsOrderCheckDetail, MOrderCheckDetail.class);
                    this.save(existsOrderCheckDetail);
                } else if (StringUtil.isBlank(existsOrderCheckDetail.getTitle())) {
                    existsOrderCheckDetail.setTitle(distributionCompany.getName());
                    existsOrderCheckDetail.setGmtModified(LocalDateTime.now());
                    this.updateById(existsOrderCheckDetail);
                }
            }

        } else if (StringUtil.isBlank(existsOrderCheckDetail.getTitle())) {
            existsOrderCheckDetail.setTitle(distributionCompany.getName());
            existsOrderCheckDetail.setGmtModified(LocalDateTime.now());
            this.updateById(existsOrderCheckDetail);
        }
        return existsOrderCheckDetail;
    }

    /**
     * 根据日期和配送单位id获取title
     *
     * @param orderDate
     * @param distributionCompanyId
     * @return
     */
    @Override
    public String getTitle(Long orderDate, Long distributionCompanyId) {
        MOrderCheckDetail existsOrderCheckDetail = this.getOKByOrderDateAndDistributionCompanyId(orderDate, distributionCompanyId);
        return existsOrderCheckDetail.getTitle();
    }


}
