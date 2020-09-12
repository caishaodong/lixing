package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.PriceCategoryDTO;
import com.shaoxing.lixing.domain.entity.MPriceCategory;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.service.MPriceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 价目管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/priceCategory")
public class MPriceCategoryController extends BaseController {
    @Autowired
    private MPriceCategoryService priceCategoryService;

    /**
     * 获取价目列表（分页）
     *
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<MPriceCategory>> getListPage(@RequestBody PriceCategoryDTO dto) {

        IPage<MPriceCategory> page = priceCategoryService.getListPage(dto);
        return success(page);
    }

    /**
     * 获取价目列表（不分页）
     *
     * @return
     */
    @GetMapping("/getList")
    public ResponseResult<MPriceCategory> getList() {

        List<MPriceCategory> page = priceCategoryService.getList();
        return success(page);
    }

}
