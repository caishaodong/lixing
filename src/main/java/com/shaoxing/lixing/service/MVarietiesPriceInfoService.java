package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MVarietiesPriceInfo;
import com.shaoxing.lixing.global.ResponseResult;

import java.util.List;

/**
 * <p>
 * 品种价格信息表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface MVarietiesPriceInfoService extends IService<MVarietiesPriceInfo> {

    MVarietiesPriceInfo getOkById(Long id);

    /**
     * 获取品种价格列表 分页
     *
     * @param dto
     * @return
     */
    IPage<MVarietiesPriceInfo> getListPage(VarietiesPriceInfoSearchDTO dto);

    /**
     * 获取品种价格列表 不分页
     *
     * @param priceCategoryId
     * @return
     */
    List<MVarietiesPriceInfo> getListByPriceCategoryId(Long priceCategoryId);

}
