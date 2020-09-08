package com.shaoxing.lixing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shaoxing.lixing.domain.dto.DistributionCompanyDTO;
import com.shaoxing.lixing.domain.dto.DistributionCompanySearchDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.vo.DistributionCompanyVO;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.util.PageUtil;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 配送公司信息管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/distributionCompany")
public class MDistributionCompanyController extends BaseController {

    @Autowired
    private MDistributionCompanyService distributionCompanyService;

    /**
     * 获取配送公司列表 分页
     *
     * @param dto
     * @return
     */
    @GetMapping("/getListPage")
    public ResponseResult<PageUtil<DistributionCompanyVO>> getListPage(@RequestBody DistributionCompanySearchDTO dto) {
        IPage<DistributionCompanyVO> page = distributionCompanyService.getListPage(dto);
        return ResponseResult.success(page);
    }

    /**
     * 保存（修改）配送管理
     *
     * @param dto
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult save(@RequestBody DistributionCompanyDTO dto) {

        MDistributionCompany distributionCompany = new MDistributionCompany();
        BeanUtils.copyProperties(dto, distributionCompany);
        ReflectUtil.setCreateInfo(distributionCompany, MDistributionCompany.class);

        if (Objects.isNull(dto.getId())) {
            // 保存价目
            distributionCompanyService.saveDistributionCompany(distributionCompany);
        } else {
            // 修改价目
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
    public ResponseResult getDistributionCompanyInfo(@PathVariable("id") Long id) {

        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(id);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }

        DistributionCompanyVO distributionCompanyVO = distributionCompanyService.getDistributionCompanyInfo(distributionCompany);
        return success(distributionCompanyVO);
    }
}
