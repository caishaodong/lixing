package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MVarietiesPriceInfo;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.service.MVarietiesPriceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 品种价格信息
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/varieties/price/info")
public class MVarietiesPriceInfoController extends BaseController {
    @Autowired
    private MVarietiesPriceInfoService varietiesPriceInfoService;

    /**
     * 获取价目列表 分页
     *
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<MVarietiesPriceInfo>> getListPage(@RequestBody VarietiesPriceInfoSearchDTO dto) {
        IPage<MVarietiesPriceInfo> page = varietiesPriceInfoService.getListPage(dto);
        return ResponseResult.success(page);
    }


    /**
     * 保存（修改）价目
     *
     * @param dto
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody VarietiesPriceInfoDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        MVarietiesPriceInfo varietiesPriceInfo = new MVarietiesPriceInfo();
        BeanUtils.copyProperties(dto, varietiesPriceInfo);
        ReflectUtil.setCreateInfo(varietiesPriceInfo, MVarietiesPriceInfo.class);

        if (Objects.isNull(dto.getId())) {
            // 保存价目
            varietiesPriceInfoService.save(varietiesPriceInfo);
        } else {
            // 修改价目
            varietiesPriceInfoService.updateById(varietiesPriceInfo);
        }
        return success();
    }

    /**
     * 删除价目
     *
     * @param id 价目id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {

        MVarietiesPriceInfo varietiesPriceInfo = varietiesPriceInfoService.getOkById(id);
        if (Objects.isNull(varietiesPriceInfo)) {
            return success();
        }

        varietiesPriceInfo.setIsDeleted(YesNoEnum.YES.getValue());
        // 删除价目
        varietiesPriceInfoService.updateById(varietiesPriceInfo);
        return success();
    }

    /**
     * 客户绑定价目
     *
     * @param dto
     * @return
     */
    @PostMapping("/customersBindingPriceCategory")
    public ResponseResult customersBindingPriceCategory(@RequestBody CustomerBindingPriceCategoryDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        ResponseResult responseResult = varietiesPriceInfoService.customersBindingPriceCategory(dto);
        return responseResult;
    }

}
