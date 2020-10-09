package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.dto.DistributionCompanySearchDTO;
import com.shaoxing.lixing.domain.entity.*;
import com.shaoxing.lixing.domain.vo.CustomerInfoVO;
import com.shaoxing.lixing.domain.vo.DistributionCompanyExportVO;
import com.shaoxing.lixing.domain.vo.DistributionCompanyVO;
import com.shaoxing.lixing.domain.vo.PriceCategoryVO;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.mapper.MDistributionCompanyMapper;
import com.shaoxing.lixing.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
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
     * 获取配送公司列表（分页）
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<DistributionCompanyVO> getListPage(DistributionCompanySearchDTO dto) {

        IPage<MDistributionCompany> page = this.page(dto, new LambdaQueryWrapper<MDistributionCompany>()
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MDistributionCompany::getGmtModified));

        List<DistributionCompanyVO> distributionCompanyVOList = getMDistributionCompanyVOList(page.getRecords());
        PageUtil<DistributionCompanyVO> pageUtil2 = new PageUtil<>();
        pageUtil2.setTotal(dto.getTotal());
        pageUtil2.setCurrent(dto.getCurrent());
        pageUtil2.setSize(dto.getSize());
        pageUtil2.setRecords(distributionCompanyVOList);

        return pageUtil2;
    }

    /**
     * 获取配送公司列表（不分页）
     *
     * @return
     */
    @Override
    public List<MDistributionCompany> getList() {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MDistributionCompany>()
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue()));
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
        ReflectUtil.setCreateInfo(distributionCompany, MDistributionCompany.class);
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
        distributionCompany.setGmtModified(LocalDateTime.now());
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
        distributionCompany.setGmtModified(LocalDateTime.now());
        this.baseMapper.updateById(distributionCompany);
        // 解绑客户和配送公司关系
        customerDistributionCompanyRelService.remove(new LambdaQueryWrapper<MCustomerDistributionCompanyRel>()
                .eq(MCustomerDistributionCompanyRel::getDistributionCompanyId, distributionCompany.getId()));
    }

    /**
     * 根据配送单位名称获取配送单位
     *
     * @param distributionCompanyName
     * @return
     */
    @Override
    public MDistributionCompany getByName(String distributionCompanyName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<MDistributionCompany>().eq(MDistributionCompany::getName, distributionCompanyName)
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取配送公司信息
     *
     * @return
     */
    @Override
    public List<DistributionCompanyExportVO> getDistributionCompanyExportVOList() {
        // 获取配送公司信息
        List<MDistributionCompany> distributionCompanyList = this.baseMapper.selectList(new LambdaQueryWrapper<MDistributionCompany>()
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(MDistributionCompany::getGmtModified));

        // 获取配送公司对应的客户信息
        List<DistributionCompanyVO> distributionCompanyVOList = getMDistributionCompanyVOList(distributionCompanyList);

        // 重新组装配送公司信息
        List<DistributionCompanyExportVO> list = convertDistributionCompanyVOListToExport(distributionCompanyVOList);
        return list;
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
            list.add(distributionCompanyVO);

            BeanUtils.copyProperties(distributionCompany, distributionCompanyVO);
            List<CustomerInfoVO> customerInfoVOList = new ArrayList<>();
            distributionCompanyVO.setCustomerInfoVoList(customerInfoVOList);


            // 根据配送公司id，获取绑定的用户列表
            List<MCustomerDistributionCompanyRel> customerDistributionCompanyRelList = customerDistributionCompanyRelService.list(new LambdaQueryWrapper<MCustomerDistributionCompanyRel>().eq(MCustomerDistributionCompanyRel::getDistributionCompanyId, distributionCompany.getId())
                    .eq(MCustomerDistributionCompanyRel::getIsDeleted, YesNoEnum.NO.getValue()));
            if (!CollectionUtils.isEmpty(customerDistributionCompanyRelList)) {
                // 获取客户id
                List<Long> customerIdList = customerDistributionCompanyRelList.stream().map(MCustomerDistributionCompanyRel::getCustomerId).collect(Collectors.toList());
                // 获取客户信息
                List<MCustomerInfo> customerInfoList = customerInfoService.list(new LambdaQueryWrapper<MCustomerInfo>().in(MCustomerInfo::getId, customerIdList)
                        .eq(MCustomerInfo::getIsDeleted, YesNoEnum.NO.getValue()));
                if (!CollectionUtils.isEmpty(customerInfoList)) {
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
                        if (!CollectionUtils.isEmpty(customerPriceCategoryRelList)) {
                            // 获取价目id
                            List<Long> priceCategoryIdList = customerPriceCategoryRelList.stream().map(MCustomerPriceCategoryRel::getPriceCategoryId).collect(Collectors.toList());
                            // 获取价目信息
                            List<MPriceCategory> priceCategoryList = priceCategoryService.list(new LambdaQueryWrapper<MPriceCategory>().in(MPriceCategory::getId, priceCategoryIdList)
                                    .eq(MPriceCategory::getIsDeleted, YesNoEnum.NO.getValue()));
                            if (!CollectionUtils.isEmpty(priceCategoryIdList)) {
                                for (MPriceCategory priceCategory : priceCategoryList) {
                                    // 封装价目信息
                                    PriceCategoryVO priceCategoryVO = new PriceCategoryVO();
                                    BeanUtils.copyProperties(priceCategory, priceCategoryVO);
                                    priceCategoryVOList.add(priceCategoryVO);
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     * 重新组装配送公司信息
     *
     * @param distributionCompanyVOList
     * @return
     */
    private List<DistributionCompanyExportVO> convertDistributionCompanyVOListToExport(List<DistributionCompanyVO> distributionCompanyVOList) {
        List<DistributionCompanyExportVO> list = new ArrayList<>();

        for (DistributionCompanyVO distributionCompanyVO : distributionCompanyVOList) {
            List<CustomerInfoVO> customerInfoVoList = distributionCompanyVO.getCustomerInfoVoList();
            if (CollectionUtils.isEmpty(customerInfoVoList)) {
                continue;
            }
            for (CustomerInfoVO customerInfoVO : customerInfoVoList) {
                List<PriceCategoryVO> priceCategoryVOList = customerInfoVO.getPriceCategoryVOList();
                if (CollectionUtils.isEmpty(priceCategoryVOList)) {
                    continue;
                }
                for (PriceCategoryVO priceCategoryVO : priceCategoryVOList) {
                    DistributionCompanyExportVO distributionCompanyExportVO = new DistributionCompanyExportVO();
                    // 数组重组
                    assemble(distributionCompanyVO, customerInfoVO, priceCategoryVO, distributionCompanyExportVO);
                    list.add(distributionCompanyExportVO);
                }
            }
        }
        return list;
    }

    /**
     * 数据重组
     *
     * @param distributionCompanyVO
     * @param customerInfoVO
     * @param priceCategoryVO
     * @param distributionCompanyExportVO
     */
    private void assemble(DistributionCompanyVO distributionCompanyVO, CustomerInfoVO customerInfoVO, PriceCategoryVO priceCategoryVO, DistributionCompanyExportVO distributionCompanyExportVO) {
        // 配送公司信息
        distributionCompanyExportVO.setDistributionCompanyId(distributionCompanyVO.getId());
        distributionCompanyExportVO.setDistributionCompanyName(distributionCompanyVO.getName());
        distributionCompanyExportVO.setSettlementDeductionRate(distributionCompanyVO.getSettlementDeductionRate());
        distributionCompanyExportVO.setAreaCode(distributionCompanyVO.getAreaCode());
        distributionCompanyExportVO.setAreaName(distributionCompanyVO.getAreaName());
        distributionCompanyExportVO.setAddress(StringUtil.concatString(distributionCompanyVO.getAreaName(), distributionCompanyExportVO.getAddress()));
        distributionCompanyExportVO.setContactUserName(distributionCompanyVO.getContactUserName());
        distributionCompanyExportVO.setContactUserMobile(distributionCompanyVO.getContactUserMobile());
        distributionCompanyExportVO.setOrderManagerName(distributionCompanyVO.getOrderManagerName());
        distributionCompanyExportVO.setOrderManagerMobile(distributionCompanyVO.getOrderManagerMobile());
        distributionCompanyExportVO.setFinancialContactName(distributionCompanyVO.getFinancialContactName());
        distributionCompanyExportVO.setFinancialContactMobile(distributionCompanyVO.getFinancialContactMobile());
        distributionCompanyExportVO.setNeedInvoice(distributionCompanyVO.getNeedInvoice());
        distributionCompanyExportVO.setNeedInvoiceStr(Objects.isNull(distributionCompanyVO.getNeedInvoice()) ? "否" :
                (YesNoEnum.YES.getValue().equals(distributionCompanyVO.getNeedInvoice()) ? "是" : "否"));
        distributionCompanyExportVO.setRemark(distributionCompanyVO.getRemark());

        // 客户信息
        distributionCompanyExportVO.setCustomerId(customerInfoVO.getId());
        distributionCompanyExportVO.setCustomerName(customerInfoVO.getName());

        // 价目信息
        distributionCompanyExportVO.setPriceCategoryId(priceCategoryVO.getId());
        distributionCompanyExportVO.setPriceCategoryName(priceCategoryVO.getName());

    }
}
