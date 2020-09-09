package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.CompanyBillSearchDTO;
import com.shaoxing.lixing.domain.dto.FCompanyBillDTO;
import com.shaoxing.lixing.domain.dto.VarietiesPriceInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.FCompanyBill;
import com.shaoxing.lixing.domain.entity.MVarietiesPriceInfo;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.service.FCompanyBillService;
import com.shaoxing.lixing.service.SysCityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<FCompanyBill>> getListPage(@RequestBody CompanyBillSearchDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        IPage<FCompanyBill> page = companyBillService.getListPage(dto);
        return success(page);
    }

    /**
     * 保存公司账单
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
        ReflectUtil.setCreateInfo(companyBill, FCompanyBill.class);
        // 根据城市编码获取城市名称
        String areaName = sysCityService.getNameByAreaCode(companyBill.getAreaCode());
        companyBill.setAreaName(areaName);
        companyBillService.save(companyBill);
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
        companyBillService.updateById(companyBill);
        return success();
    }

}
