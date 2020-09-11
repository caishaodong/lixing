package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.entity.MPriceCategory;

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
     * 根据价目名称获取价目
     *
     * @param priceCategoryName
     * @return
     */
    MPriceCategory getByName(String priceCategoryName);
}
