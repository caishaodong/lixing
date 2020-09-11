package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.service.MVarietiesPriceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 品种价格信息管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/varietiesPriceInfo")
public class MVarietiesPriceInfoController extends BaseController {
    @Autowired
    private MVarietiesPriceInfoService varietiesPriceInfoService;

    /**
     * 获取品种价格列表（分页）
     *
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<MVarietiesPriceInfo>> getListPage(@RequestBody VarietiesPriceInfoSearchDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        IPage<MVarietiesPriceInfo> page = varietiesPriceInfoService.getListPage(dto);
        return success(page);
    }

    /**
     * 根据价目id获取品种价格列表（不分页）
     *
     * @param priceCategoryId 价目id
     * @return
     */
    @GetMapping("/getListByPriceCategoryId/{priceCategoryId}")
    public ResponseResult<MVarietiesPriceInfo> getList(@PathVariable("priceCategoryId") Long priceCategoryId) {
        // 根据价目id获取品种价格信息
        List<MVarietiesPriceInfo> list = varietiesPriceInfoService.getListByPriceCategoryId(priceCategoryId);
        return success(list);
    }


    /**
     * 保存（修改）品种价格
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody VarietiesPriceInfoDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        MVarietiesPriceInfo varietiesPriceInfo = new MVarietiesPriceInfo();
        BeanUtils.copyProperties(dto, varietiesPriceInfo);

        // 校验价格品种名称是否重复
        int count = varietiesPriceInfoService.count(new LambdaQueryWrapper<MVarietiesPriceInfo>().eq(MVarietiesPriceInfo::getName, dto.getName())
                .eq(MVarietiesPriceInfo::getPriceCategoryId, dto.getPriceCategoryId())
                .eq(MVarietiesPriceInfo::getIsDeleted, YesNoEnum.NO.getValue())
                .ne(Objects.nonNull(dto.getId()), MVarietiesPriceInfo::getId, dto.getId()));
        if (count > 0) {
            return error(BusinessEnum.VARIETIES_PRICE_NAME_REPEAT);
        }

        if (Objects.isNull(dto.getId())) {
            // 保存价目
            ReflectUtil.setCreateInfo(varietiesPriceInfo, MVarietiesPriceInfo.class);
            varietiesPriceInfoService.save(varietiesPriceInfo);
        } else {
            // 修改价目
            varietiesPriceInfo.setGmtModified(LocalDateTime.now());
            varietiesPriceInfoService.updateById(varietiesPriceInfo);
        }
        return success();
    }

    /**
     * 删除品种价格
     *
     * @param id 品种价格id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {

        MVarietiesPriceInfo varietiesPriceInfo = varietiesPriceInfoService.getOkById(id);
        if (Objects.isNull(varietiesPriceInfo)) {
            return success();
        }

        varietiesPriceInfo.setIsDeleted(YesNoEnum.YES.getValue());
        varietiesPriceInfo.setGmtModified(LocalDateTime.now());
        // 删除价目
        varietiesPriceInfoService.updateById(varietiesPriceInfo);
        return success();
    }

    /**
     * 导出品种价格列表
     *
     * @param priceCategoryId 价目id
     */
    @GetMapping("/export/{priceCategoryId}")
    public ResponseResult export(@PathVariable("priceCategoryId") Long priceCategoryId, HttpServletResponse response) {
        // 根据价目id获取品种价格信息
        List<MVarietiesPriceInfo> varietiesPriceInfoList = varietiesPriceInfoService.getListByPriceCategoryId(priceCategoryId);

        LinkedHashMap<String, String> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("食材品种", "name");
        fieldNameMap.put("单位", "unit");
        fieldNameMap.put("单价", "price");
        fieldNameMap.put("备注", "remark");
        fieldNameMap.put("添加时间", "gmtCreate");

        try {
            LOGGER.info("开始准备导出品种价格");
            ExcelDataUtil.export(fieldNameMap, varietiesPriceInfoList, "品种价格", response);
        } catch (Exception e) {
            LOGGER.error("品种价格导出失败", e);
            return error();
        }
        return success();
    }

}
