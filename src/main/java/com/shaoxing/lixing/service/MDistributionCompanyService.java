package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.DistributionCompanySearchDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.vo.DistributionCompanyVO;

/**
 * <p>
 * 配送公司信息表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface MDistributionCompanyService extends IService<MDistributionCompany> {

    MDistributionCompany getOKById(Long id);

    /**
     * 获取配送公司列表 分页
     *
     * @param dto
     * @return
     */
    IPage<DistributionCompanyVO> getListPage(DistributionCompanySearchDTO dto);

    /**
     * 保存价目
     *
     * @param distributionCompany
     */
    void saveDistributionCompany(MDistributionCompany distributionCompany);

    /**
     * 修改价目
     *
     * @param distributionCompany
     */
    void updateDistributionCompany(MDistributionCompany distributionCompany);

    /**
     * 获取配送公司信息
     *
     * @param distributionCompany
     * @return
     */
    DistributionCompanyVO getDistributionCompanyInfo(MDistributionCompany distributionCompany);
}
