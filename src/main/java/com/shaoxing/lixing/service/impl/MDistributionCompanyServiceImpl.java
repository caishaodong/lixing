package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.dto.DistributionCompanySearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.domain.vo.CustomerInfoVO;
import com.shaoxing.lixing.domain.vo.DistributionCompanyVO;
import com.shaoxing.lixing.domain.vo.PriceCategoryVO;
import com.shaoxing.lixing.domain.vo.VarietiesPriceInfoVO;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.mapper.MDistributionCompanyMapper;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 配送公司信息表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class MDistributionCompanyServiceImpl extends ServiceImpl<MDistributionCompanyMapper, MDistributionCompany> implements MDistributionCompanyService {

    @Autowired
    private MCustomerDistributionCompanyRelService customerDistributionCompanyRelService;
    @Autowired
    private MCustomerInfoService customerInfoService;
    @Autowired
    private MCustomerPriceCategoryRelService customerPriceCategoryRelService;
    @Autowired
    private MPriceCategoryService priceCategoryService;
    @Autowired
    private MVarietiesPriceInfoService varietiesPriceInfoService;
    @Autowired
    private SysCityService sysCityService;

    @Override
    public MDistributionCompany getOKById(Long id) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MDistributionCompany>().eq(MDistributionCompany::getId, id)
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取配送公司列表 分页
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<DistributionCompanyVO> getListPage(DistributionCompanySearchDTO dto) {
        PageUtil<MDistributionCompany> pageUtil = new PageUtil<>();
        pageUtil.setCurrent(dto.getCurrent());
        pageUtil.setSize(dto.getSize());
        IPage<MDistributionCompany> page = this.page(pageUtil, new LambdaQueryWrapper<MDistributionCompany>()
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MDistributionCompany::getGmtModified));

        List<DistributionCompanyVO> distributionCompanyVOList = getMDistributionCompanyVOList(page.getRecords());
        PageUtil<DistributionCompanyVO> pageUtil2 = new PageUtil<>();
        pageUtil2.setTotal(pageUtil.getTotal());
        pageUtil2.setCurrent(pageUtil.getCurrent());
        pageUtil2.setSize(pageUtil.getSize());
        pageUtil2.setRecords(distributionCompanyVOList);

        return pageUtil2;
    }

    /**
     * 保存配送公司信息
     *
     * @param distributionCompany
     */
    @Override
    public void saveDistributionCompany(MDistributionCompany distributionCompany) {
        String areaName = "";
        Integer areaCode;
        if (Objects.nonNull(areaCode = distributionCompany.getAreaCode())) {
            areaName = sysCityService.getNameByAreaCode(areaCode);
        }
        distributionCompany.setAreaName(areaName);
        this.baseMapper.insert(distributionCompany);
    }

    /**
     * 修改配送公司信息
     *
     * @param distributionCompany
     */
    @Override
    public void updateDistributionCompany(MDistributionCompany distributionCompany) {
        String areaName = "";
        Integer areaCode;
        if (Objects.nonNull(areaCode = distributionCompany.getAreaCode())) {
            areaName = sysCityService.getNameByAreaCode(areaCode);
        }
        distributionCompany.setAreaName(areaName);
        this.baseMapper.updateById(distributionCompany);
    }

    /**
     * 获取配送公司信息
     *
     * @param distributionCompany
     * @return
     */
    @Override
    public DistributionCompanyVO getDistributionCompanyInfo(MDistributionCompany distributionCompany) {
        List<MDistributionCompany> list = new ArrayList<>();
        list.add(distributionCompany);
        List<DistributionCompanyVO> mDistributionCompanyVOList = getMDistributionCompanyVOList(list);

        return CollectionUtils.isEmpty(mDistributionCompanyVOList) ? null : mDistributionCompanyVOList.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDistributionCompany(MDistributionCompany distributionCompany) {
        // 删除配送公司
        distributionCompany.setIsDeleted(YesNoEnum.YES.getValue());
        this.baseMapper.updateById(distributionCompany);
        // 解绑客户和配送公司关系
        customerDistributionCompanyRelService.remove(new LambdaQueryWrapper<MCustomerDistributionCompanyRel>()
                .eq(MCustomerDistributionCompanyRel::getDistributionCompanyId, distributionCompany.getId()));
    }

    /**
     * 获取配送管理绑定的用户信息
     *
     * @param distributionCompanyList
     * @return
     */
    public List<DistributionCompanyVO> getMDistributionCompanyVOList(List<MDistributionCompany> distributionCompanyList) {
        List<DistributionCompanyVO> list = new ArrayList<>();
        if (Objects.isNull(distributionCompanyList) || distributionCompanyList.isEmpty()) {
            return list;
        }
        for (MDistributionCompany distributionCompany : distributionCompanyList) {
            DistributionCompanyVO distributionCompanyVO = new DistributionCompanyVO();
            BeanUtils.copyProperties(distributionCompany, distributionCompanyVO);
            List<CustomerInfoVO> customerInfoVOList = new ArrayList<>();
            distributionCompanyVO.setCustomerInfoVoList(customerInfoVOList);


            // 根据配送公司id，获取绑定的用户列表
            List<MCustomerDistributionCompanyRel> customerDistributionCompanyRelList = customerDistributionCompanyRelService.list(new LambdaQueryWrapper<MCustomerDistributionCompanyRel>().eq(MCustomerDistributionCompanyRel::getDistributionCompanyId, distributionCompany.getId())
                    .eq(MCustomerDistributionCompanyRel::getIsDeleted, YesNoEnum.NO.getValue()));
            if (Objects.nonNull(customerDistributionCompanyRelList) && !customerDistributionCompanyRelList.isEmpty()) {
                // 获取客户id
                List<Long> customerIdList = customerDistributionCompanyRelList.stream().map(MCustomerDistributionCompanyRel::getCustomerId).collect(Collectors.toList());
                // 获取客户信息
                List<MCustomerInfo> customerInfoList = customerInfoService.list(new LambdaQueryWrapper<MCustomerInfo>().in(MCustomerInfo::getId, customerIdList)
                        .eq(MCustomerInfo::getIsDeleted, YesNoEnum.NO.getValue()));
                if (Objects.nonNull(customerInfoList) && !customerInfoList.isEmpty()) {
                    for (MCustomerInfo mCustomerInfo : customerInfoList) {
                        // 封装客户信息
                        CustomerInfoVO customerInfoVO = new CustomerInfoVO();
                        BeanUtils.copyProperties(mCustomerInfo, customerInfoVO);
                        customerInfoVOList.add(customerInfoVO);
                        List<PriceCategoryVO> priceCategoryVOList = new ArrayList<>();
                        customerInfoVO.setPriceCategoryVOList(priceCategoryVOList);


                        // 根据客户id，获取绑定的价目列表
                        List<MCustomerPriceCategoryRel> customerPriceCategoryRelList = customerPriceCategoryRelService.list(new LambdaQueryWrapper<MCustomerPriceCategoryRel>().eq(MCustomerPriceCategoryRel::getCustomerId, mCustomerInfo.getId())
                                .eq(MCustomerPriceCategoryRel::getIsDeleted, YesNoEnum.NO.getValue()));
                        if (Objects.nonNull(customerPriceCategoryRelList) && !customerPriceCategoryRelList.isEmpty()) {
                            // 获取价目id
                            List<Long> priceCategoryIdList = customerPriceCategoryRelList.stream().map(MCustomerPriceCategoryRel::getPriceCategoryId).collect(Collectors.toList());
                            // 获取价目信息
                            List<MPriceCategory> priceCategoryList = priceCategoryService.list(new LambdaQueryWrapper<MPriceCategory>().in(MPriceCategory::getId, priceCategoryIdList)
                                    .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
                            if (Objects.nonNull(priceCategoryIdList) && !priceCategoryList.isEmpty()) {
                                for (MPriceCategory priceCategory : priceCategoryList) {
                                    // 封装价目信息
                                    PriceCategoryVO priceCategoryVO = new PriceCategoryVO();
                                    BeanUtils.copyProperties(priceCategory, priceCategoryVO);
                                    priceCategoryVOList.add(priceCategoryVO);
                                    List<VarietiesPriceInfoVO> varietiesPriceInfoVOList = new ArrayList<>();


                                    // 根据价目id， 获取价目下面的价格信息
                                    List<MVarietiesPriceInfo> varietiesPriceInfoList = varietiesPriceInfoService.list(new LambdaQueryWrapper<MVarietiesPriceInfo>().eq(MVarietiesPriceInfo::getPriceCategoryId, priceCategory.getId())
                                            .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO.getValue()));
                                    if (Objects.nonNull(varietiesPriceInfoList) && !varietiesPriceInfoList.isEmpty()) {
                                        for (MVarietiesPriceInfo varietiesPriceInfo : varietiesPriceInfoList) {
                                            // 封装价目下面的价格信息
                                            VarietiesPriceInfoVO varietiesPriceInfoVO = new VarietiesPriceInfoVO();
                                            BeanUtils.copyProperties(varietiesPriceInfo, varietiesPriceInfoVO);
                                            varietiesPriceInfoVOList.add(varietiesPriceInfoVO);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }
}
