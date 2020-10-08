package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.OrderInfoExportDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.decimal.DecimalUtil;
import com.shaoxing.lixing.mapper.MOrderInfoMapper;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private MDistributionCompanyService distributionCompanyService;
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MPriceCategoryService priceCategoryService;
    @Autowired
    private MVarietiesPriceInfoService varietiesPriceInfoService;

    @Override
    public MOrderInfo getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MOrderInfo>().eq(MOrderInfo::getId, id)
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取订单列表（分页）
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<MOrderInfo> getListPage(OrderInfoSearchDTO dto) {

        IPage<MOrderInfo> page = this.page(dto, new LambdaQueryWrapper<MOrderInfo>()
                .eq(Objects.nonNull(dto.getOrderDate()), MOrderInfo::getOrderDate, dto.getOrderDate())
                .ge(Objects.nonNull(dto.getStartOrderDate()), MOrderInfo::getOrderDate, dto.getStartOrderDate())
                .le(Objects.nonNull(dto.getEndOrderDate()), MOrderInfo::getOrderDate, dto.getEndOrderDate())
                .eq(MOrderInfo::getDistributionCompanyId, dto.getDistributionCompanyId())
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));
        return page;
    }

    /**
     * 订单导入信息校验
     *
     * @param orderInfo
     * @return
     */
    @Override
    public ResponseResult dataCheck(MOrderInfo orderInfo) {
        // 根据配送单位名称获取配送单位id
        MDistributionCompany distributionCompany = distributionCompanyService.getByName(orderInfo.getDistributionCompanyName());
        if (Objects.isNull(distributionCompany)) {
            return ResponseResult.error(BusinessEnum.DISTRIBUTION_COMPANY_NAME_NOT_EXIST);
        }
        orderInfo.setDistributionCompanyId(distributionCompany.getId());

        // 根据客户名称获取客户id
        MCustomerInfo customerInfo = customerInfoService.getByName(orderInfo.getCustomerName());
        if (Objects.isNull(customerInfo)) {
            return ResponseResult.error(BusinessEnum.CUSTOMER__NAME_NOT_EXIST);
        }
        orderInfo.setCustomerId(customerInfo.getId());

        // 根据价目名称获取价目id
        MPriceCategory priceCategory = priceCategoryService.getByName(orderInfo.getPriceCategoryName());
        if (Objects.isNull(priceCategory)) {
            return ResponseResult.error(BusinessEnum.PRICE_CATEGORY_NAME_NOT_EXIST);
        }
        orderInfo.setPriceCategoryId(priceCategory.getId());

        // 根据价目id和品种名称获取品种id，品种单价
        MVarietiesPriceInfo varietiesPriceInfo = varietiesPriceInfoService.getByPriceCategoryIdAndName(priceCategory.getId(), orderInfo.getVarietiesName());
        if (Objects.isNull(varietiesPriceInfo)) {
            return ResponseResult.error(BusinessEnum.VARIETIES_PRICE_NAME_NOT_EXIST);
        }
        orderInfo.setVarietiesPriceId(varietiesPriceInfo.getId());
        orderInfo.setPrice(varietiesPriceInfo.getPrice());
        orderInfo.setUnit(varietiesPriceInfo.getUnit());

        // 根据数量和单价，计算总价
        orderInfo.setTotalPrice(DecimalUtil.multiply(varietiesPriceInfo.getPrice(), orderInfo.getNum()));
        return null;
    }

    /**
     * 获取订单列表（不分页）
     *
     * @param dto
     * @return
     */
    @Override
    public List<MOrderInfo> getList(OrderInfoExportDTO dto) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MOrderInfo>()
                .eq(Objects.nonNull(dto.getOrderDate()), MOrderInfo::getOrderDate, dto.getOrderDate())
                .ge(Objects.nonNull(dto.getStartOrderDate()), MOrderInfo::getOrderDate, dto.getStartOrderDate())
                .le(Objects.nonNull(dto.getEndOrderDate()), MOrderInfo::getOrderDate, dto.getEndOrderDate())
                .eq(MOrderInfo::getDistributionCompanyId, dto.getDistributionCompanyId())
                .eq(MOrderInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MOrderInfo::getGmtModified));
    }
}
