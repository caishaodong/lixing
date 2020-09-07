package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.MVarietiesPriceInfoSearchDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public IPage<MVarietiesPriceInfo> getListPage(MVarietiesPriceInfoSearchDTO dto) {
        PageUtil<MVarietiesPriceInfo> pageUtil = new PageUtil<>();
        IPage<MVarietiesPriceInfo> page = this.page(pageUtil, new LambdaQueryWrapper<MVarietiesPriceInfo>()
                .eq(MVarietiesPriceInfo::getPriceCategoryId, dto.getPriceCategoryId()));
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
        List<Long> custormerIdList = dto.getCustormerIdList();
        int count = customerInfoService.count(new LambdaQueryWrapper<MCustomerInfo>().in(MCustomerInfo::getId, custormerIdList));
        if (count != custormerIdList.size()) {
            return ResponseResult.error(BusinessEnum.CUSTOMER_INFO_ERROR);
        }
        MPriceCategory priceCategory = priceCategoryService.getOKById(dto.getPriceCategoryId());
        if (Objects.isNull(priceCategory)) {
            return ResponseResult.error(BusinessEnum.PRICE_CATEGORY_INFO_ERROR);
        }

        List<MCustomerPriceCategoryRel> list = new ArrayList<>();
        custormerIdList.forEach(custormerId -> {
            MCustomerPriceCategoryRel rel = new MCustomerPriceCategoryRel();
            rel.setCustomerId(custormerId);
            rel.setPriceCategoryId(dto.getPriceCategoryId());
            list.add(rel);
        });
        customerPriceCategoryRelService.saveBatch(list);

        return ResponseResult.success();
    }
}
