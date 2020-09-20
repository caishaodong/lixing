package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.CustomerInfoDTO;
import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import com.shaoxing.lixing.global.ResponseResult;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface MCustomerInfoService extends IService<MCustomerInfo> {

    MCustomerInfo getOKById(Long id);

    /**
     * 添加客户，并绑定配送公司
     *
     * @param customerInfoDTO
     * @return
     */
    MCustomerInfo saveAndBindDistributionCompany(CustomerInfoDTO customerInfoDTO);

    /**
     * 解绑客户和配送公司关系
     *
     * @param customerInfoDTO
     */
    void unBindDistribution(CustomerInfoDTO customerInfoDTO);

    /**
     * 根据配送公司id获取绑定的客户列表
     *
     * @param distributionCompanyId
     * @return
     */
    List<MCustomerInfo> getCustomerListByDistributionCompanyId(Long distributionCompanyId);

    /**
     * 客户绑定价目
     *
     * @param dto
     * @return
     */
    ResponseResult customersBindingPriceCategory(CustomerBindingPriceCategoryDTO dto);

    /**
     * 根据客户名称获取客户
     *
     * @param customerName
     * @return
     */
    MCustomerInfo getByName(String customerName);

    /**
     * 获取全部客户列表（不分页）
     *
     * @return
     */
    List<MCustomerInfo> getList();

    /**
     * 根据价目id获取绑定的客户id
     *
     * @param priceCategoryId
     * @return
     */
    List<Long> getCustomerIdList(Long priceCategoryId);
}
