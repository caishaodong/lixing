package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.CompanyBillSearchDTO;
import com.shaoxing.lixing.domain.entity.FCompanyBill;

/**
 * <p>
 * 公司账单表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface FCompanyBillService extends IService<FCompanyBill> {

    FCompanyBill getOKById(Long id);

    /**
     * 获取公司账单列表（分页）
     *
     * @param dto
     * @return
     */
    IPage<FCompanyBill> getListPage(CompanyBillSearchDTO dto);
}
