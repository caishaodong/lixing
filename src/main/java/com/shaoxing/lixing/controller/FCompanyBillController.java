package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.dto.FCompanyBillDTO;
import com.shaoxing.lixing.domain.entity.FCompanyBill;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.service.FCompanyBillService;
import com.shaoxing.lixing.service.SysCityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公司账单管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/companyBill")
public class FCompanyBillController extends BaseController {
    @Autowired
    private SysCityService sysCityService;
    @Autowired
    private FCompanyBillService companyBillService;

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

}
