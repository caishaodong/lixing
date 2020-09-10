package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.CustomerBindingPriceCategoryDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
        ReflectUtil.setCreateInfo(varietiesPriceInfo, MVarietiesPriceInfo.class);

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
            varietiesPriceInfoService.save(varietiesPriceInfo);
        } else {
            // 修改价目
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

    /**
     * 导出品种价格列表
     *
     * @param priceCategoryId
     */
    @GetMapping("/export/{priceCategoryId}")
    public void export(@PathVariable("priceCategoryId") Long priceCategoryId, HttpServletResponse response) {
        // 根据价目id获取品种价格信息
        List<MVarietiesPriceInfo> varietiesPriceInfoList = varietiesPriceInfoService.getListByPriceCategoryId(priceCategoryId);

        LinkedHashMap<String, String> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("食材品种", "name");
        fieldNameMap.put("单位", "unit");
        fieldNameMap.put("单价", "price");
        fieldNameMap.put("备注", "remark");
        fieldNameMap.put("添加时间", "gmtCreate");

        try {
            String fileName = "品种价格";
            ExcelDataUtil.export(fieldNameMap, varietiesPriceInfoList, fileName, response);
        } catch (Exception e) {
            System.out.println("导出失败");
            e.printStackTrace();
        }
    }

}
