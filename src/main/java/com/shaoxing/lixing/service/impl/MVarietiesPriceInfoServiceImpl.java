package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.domain.entity.MCustomerPriceCategoryRel;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.domain.entity.MVarietiesPriceInfo;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.mapper.MVarietiesPriceInfoMapper;
import com.shaoxing.lixing.service.MCustomerInfoService;
import com.shaoxing.lixing.service.MCustomerPriceCategoryRelService;
import com.shaoxing.lixing.service.MPriceCategoryService;
import com.shaoxing.lixing.service.MVarietiesPriceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MPriceCategoryService priceCategoryService;
    @Autowired
    private MCustomerPriceCategoryRelService customerPriceCategoryRelService;

    @Override
    public MVarietiesPriceInfo getOkById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MVarietiesPriceInfo>().eq(MVarietiesPriceInfo::getId, id)
                .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO));
    }

    /**
     * 查询价目列表 分页
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
     * 客户绑定价目
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult customersBindingPriceCategory(CustomerBindingPriceCategoryDTO dto) {
        // 客户信息校验
        List<Long> custormerIdList = dto.getCustormerIdList();
        int count = customerInfoService.count(new LambdaQueryWrapper<MCustomerInfo>().in(MCustomerInfo::getId, custormerIdList));
        if (count != custormerIdList.size()) {
            return ResponseResult.error(BusinessEnum.CUSTOMER_INFO_ERROR);
        }
        // 价目信息校验
        MPriceCategory priceCategory = priceCategoryService.getOKById(dto.getPriceCategoryId());
        if (Objects.isNull(priceCategory)) {
            return ResponseResult.error(BusinessEnum.PRICE_CATEGORY_INFO_ERROR);
        }
        // 获取已经绑定过改价目的客户关系
        List<MCustomerPriceCategoryRel> customerPriceCategoryRelList = customerPriceCategoryRelService.list(new LambdaQueryWrapper<MCustomerPriceCategoryRel>()
                .in(MCustomerPriceCategoryRel::getCustomerId, custormerIdList)
                .eq(MCustomerPriceCategoryRel::getPriceCategoryId, priceCategory)
                .eq(MCustomerPriceCategoryRel::getIsDeleted, YesNoEnum.NO.getValue()));
        List<Long> existCustomerIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(customerPriceCategoryRelList)) {
            existCustomerIds = customerPriceCategoryRelList.stream().map(MCustomerPriceCategoryRel::getCustomerId).collect(Collectors.toList());
        }

        // 客户绑定价目
        List<MCustomerPriceCategoryRel> list = new ArrayList<>();
        for (Long customerId : custormerIdList) {
            Long id = null;
            if (existCustomerIds.contains(customerId)) {
                MCustomerPriceCategoryRel rel = customerPriceCategoryRelList.stream()
                        .filter(customerPriceCategoryRel -> customerId.equals(customerPriceCategoryRel.getCustomerId())).findAny().get();
                id = rel.getId();
            }
            MCustomerPriceCategoryRel rel = new MCustomerPriceCategoryRel();
            rel.setCustomerId(customerId);
            rel.setPriceCategoryId(dto.getPriceCategoryId());
            rel.setId(id);
            list.add(rel);
        }
        customerPriceCategoryRelService.saveOrUpdateBatch(list);

        return ResponseResult.success();
    }
}
