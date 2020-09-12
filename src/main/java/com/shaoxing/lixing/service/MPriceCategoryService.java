package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.PriceCategoryDTO;
import com.shaoxing.lixing.domain.entity.MPriceCategory;

import java.util.List;

/**
 * <p>
 * 价目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface MPriceCategoryService extends IService<MPriceCategory> {
    MPriceCategory getOKById(Long id);

    /**
     * 获取价目列表（分页）
     *
     * @param dto
     * @return
     */
    IPage<MPriceCategory> getListPage(PriceCategoryDTO dto);

    /**
     * 根据价目名称获取价目
     *
     * @param priceCategoryName
     * @return
     */
    MPriceCategory getByName(String priceCategoryName);

    /**
     * 获取价目列表（不分页）
     *
     * @return
     */
    List<MPriceCategory> getList();
}
