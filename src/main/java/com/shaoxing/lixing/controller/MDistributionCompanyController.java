package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.DistributionCompanyDTO;
import com.shaoxing.lixing.domain.dto.DistributionCompanySearchDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.vo.DistributionCompanyExportVO;
import com.shaoxing.lixing.domain.vo.DistributionCompanyVO;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.excel.ExcelDataUtil;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 配送公司信息管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/distributionCompany")
public class MDistributionCompanyController extends BaseController {

    @Autowired
    private MDistributionCompanyService distributionCompanyService;

    /**
     * 获取配送公司列表（分页）
     *
     * @param dto
     * @return
     */
    @PostMapping("/getListPage")
    public ResponseResult<PageUtil<DistributionCompanyVO>> getListPage(@RequestBody DistributionCompanySearchDTO dto) {
        IPage<DistributionCompanyVO> page = distributionCompanyService.getListPage(dto);
        return success(page);
    }

    /**
     * 获取配送公司列表（不分页）
     *
     * @return
     */
    @GetMapping("/getList")
    public ResponseResult<List<MDistributionCompany>> getList() {
        List<MDistributionCompany> list = distributionCompanyService.getList();
        return success(list);
    }

    /**
     * 保存（修改）配送公司信息
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody DistributionCompanyDTO dto) {

        MDistributionCompany distributionCompany = new MDistributionCompany();
        BeanUtils.copyProperties(dto, distributionCompany);

        // 校验配送公司名称是否重复
        int count = distributionCompanyService.count(new LambdaQueryWrapper<MDistributionCompany>().eq(MDistributionCompany::getName, dto.getName())
                .eq(MDistributionCompany::getIsDeleted, YesNoEnum.NO.getValue())
                .ne(Objects.nonNull(dto.getId()), MDistributionCompany::getId, dto.getId()));
        if (count > 0) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NAME_REPEAT);
        }

        if (Objects.isNull(dto.getId())) {
            // 保存配送公司信息
            distributionCompanyService.saveDistributionCompany(distributionCompany);
        } else {
            // 修改配送公司信息
            distributionCompanyService.updateDistributionCompany(distributionCompany);
        }
        return success();
    }


    /**
     * 获取配送公司信息
     *
     * @param id 配送公司id
     * @return
     */
    @GetMapping("/getDistributionCompanyInfo/{id}")
    public ResponseResult<DistributionCompanyVO> getDistributionCompanyInfo(@PathVariable("id") Long id) {

        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(id);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }

        DistributionCompanyVO distributionCompanyVO = distributionCompanyService.getDistributionCompanyInfo(distributionCompany);
        return success(distributionCompanyVO);
    }

    /**
     * 删除配送公司
     *
     * @param id 配送公司id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {

        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(id);
        if (Objects.isNull(distributionCompany)) {
            return success();
        }
        // 删除配送公司
        distributionCompanyService.deleteDistributionCompany(distributionCompany);
        return success();
    }

    /**
     * 导出配送公司列表
     *
     * @return
     */
    @GetMapping("/export")
    public ResponseResult export(HttpServletResponse response) {
        // 获取配送公司信息
        List<DistributionCompanyExportVO> list = distributionCompanyService.getDistributionCompanyExportVOList();

        LinkedHashMap<String, String[]> fieldNameMap = new LinkedHashMap();
        fieldNameMap.put("配送单位", new String[]{"distributionCompanyName", Constant.COLUMN_WIDTH_40});
        fieldNameMap.put("客户名称", new String[]{"customerName", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("商品类别", new String[]{"varietiesPriceName"});
        fieldNameMap.put("价目", new String[]{"priceCategoryName"});
        fieldNameMap.put("结算扣率", new String[]{"settlementDeductionRate"});
        fieldNameMap.put("联系地址", new String[]{"address", Constant.COLUMN_WIDTH_40});
        fieldNameMap.put("联系人", new String[]{"contactUserName"});
        fieldNameMap.put("联系方式", new String[]{"contactUserMobile"});
        fieldNameMap.put("订单管理人", new String[]{"orderManagerName", Constant.COLUMN_WIDTH_17});
        fieldNameMap.put("订单管理人联系方式", new String[]{"orderManagerMobile", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("财务联系人", new String[]{"financialContactName"});
        fieldNameMap.put("财务联系人联系方式", new String[]{"financialContactMobile", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("是否需要开机打发票", new String[]{"needInvoiceStr", Constant.COLUMN_WIDTH_27});
        fieldNameMap.put("其他备注", new String[]{"remark", Constant.COLUMN_WIDTH_80});


        try {
            LOGGER.info("开始准备导出配送管理");
            ExcelDataUtil.export(null, fieldNameMap, list, "配送管理", response);
            LOGGER.info("配送管理导出完成");
        } catch (Exception e) {
            LOGGER.error("配送管理导出失败", e);
            return error();
        }
        return success();
    }

}
