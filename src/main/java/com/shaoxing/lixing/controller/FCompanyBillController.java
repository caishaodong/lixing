package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.CompanyBillSearchDTO;
import com.shaoxing.lixing.domain.dto.FCompanyBillDTO;
import com.shaoxing.lixing.domain.entity.FCompanyBill;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.excel.ExcelDataDTO;
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.service.FCompanyBillService;
import com.shaoxing.lixing.service.SysCityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 公司账单管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/companyBill")
public class FCompanyBillController extends BaseController {
    @Autowired
    private SysCityService sysCityService;
    @Autowired
    private FCompanyBillService companyBillService;

    /**
     * 获取公司账单列表（分页）
     *
     * @param dto
     * @return
     */
    @PostMapping("/getListPage")
    public ResponseResult<PageUtil<FCompanyBill>> getListPage(@RequestBody CompanyBillSearchDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        IPage<FCompanyBill> page = companyBillService.getListPage(dto);
        return success(page);
    }

    /**
     * 保存（修改）公司账单
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody FCompanyBillDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        FCompanyBill companyBill = new FCompanyBill();
        BeanUtils.copyProperties(dto, companyBill);

        if (Objects.nonNull(companyBill.getAreaCode())) {
            // 根据城市编码获取城市名称
            String areaName = sysCityService.getNameByAreaCode(companyBill.getAreaCode());
            companyBill.setAreaName(areaName);
        }

        if (Objects.isNull(dto.getId())) {
            // 保存
            ReflectUtil.setCreateInfo(companyBill, FCompanyBill.class);
            companyBillService.save(companyBill);
        } else {
            // 修改
            companyBill.setGmtModified(LocalDateTime.now());
            companyBillService.updateById(companyBill);
        }

        return success();
    }

    /**
     * 删除公司账单
     *
     * @param id 公司账单id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        FCompanyBill companyBill = companyBillService.getOKById(id);
        if (Objects.isNull(companyBill)) {
            return success();
        }
        companyBill.setIsDeleted(YesNoEnum.YES.getValue());
        companyBill.setGmtModified(LocalDateTime.now());
        companyBillService.updateById(companyBill);
        return success();
    }

    /**
     * 导出公司账单
     *
     * @param response
     * @return
     */
    @GetMapping("/export")
    public ResponseResult export(HttpServletResponse response) {
        // 获取需要导出的账单列表
        List<FCompanyBill> companyBillList = companyBillService.getList();

        LinkedHashMap<String, String[]> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("费用类别", new String[]{"feeCategoryName"});
        fieldNameMap.put("费用科目", new String[]{"feeSubjectName"});
        fieldNameMap.put("进货产地", new String[]{"address", Constant.COLUMN_WIDTH_40});
        fieldNameMap.put("日期", new String[]{"billDate"});
        fieldNameMap.put("单价", new String[]{"price"});
        fieldNameMap.put("数量", new String[]{"num"});
        fieldNameMap.put("重量", new String[]{"weight"});
        fieldNameMap.put("金额(元)", new String[]{"totalPrice"});
        fieldNameMap.put("录入人", new String[]{"enteredUserName", Constant.COLUMN_WIDTH_17});
        fieldNameMap.put("备注", new String[]{"remark", Constant.COLUMN_WIDTH_80});

        try {
            LOGGER.info("开始准备导出公司账单");
            ExcelDataUtil.export(new ExcelDataDTO<>(null, fieldNameMap, companyBillList, "账单", Boolean.TRUE), response);
            LOGGER.info("公司账单导出完成");
        } catch (Exception e) {
            LOGGER.error("公司账单导出失败", e);
            return error();
        }
        return success();
    }

}
