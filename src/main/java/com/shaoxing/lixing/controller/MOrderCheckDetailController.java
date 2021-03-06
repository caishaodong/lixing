package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.dto.OrderCheckDetailDTO;
import com.shaoxing.lixing.domain.entity.MDistributionCompany;
import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.constant.Constant;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.service.MDistributionCompanyService;
import com.shaoxing.lixing.service.MOrderCheckDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 订单送货验收信息管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/orderCheckDetail")
public class MOrderCheckDetailController extends BaseController {
    @Autowired
    private MOrderCheckDetailService orderCheckDetailService;
    @Autowired
    private MDistributionCompanyService distributionCompanyService;

    /**
     * 保存（修改）订单送货验收信息
     *
     * @param dto
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdate(@RequestBody OrderCheckDetailDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(dto.getDistributionCompanyId());
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }

        MOrderCheckDetail existsOrderCheckDetail = orderCheckDetailService.getOKByOrderDateAndDistributionCompanyId(dto.getOrderDate(), dto.getDistributionCompanyId());
        if (Objects.isNull(existsOrderCheckDetail)) {
            // 新增
            existsOrderCheckDetail = new MOrderCheckDetail();
            BeanUtils.copyProperties(dto, existsOrderCheckDetail);
            ReflectUtil.setCreateInfo(existsOrderCheckDetail, MOrderCheckDetail.class);
        } else {
            // 修改
            existsOrderCheckDetail.setDeliveryUserName(dto.getDeliveryUserName());
            existsOrderCheckDetail.setCheckUserName(dto.getCheckUserName());
            existsOrderCheckDetail.setTitle(dto.getTitle());
            existsOrderCheckDetail.setGmtModified(LocalDateTime.now());
        }
        // 校验title是否为空
        if (StringUtil.isBlank(existsOrderCheckDetail.getTitle())) {
            existsOrderCheckDetail.setTitle(Constant.COMPANY_NAME);
        }
        orderCheckDetailService.saveOrUpdate(existsOrderCheckDetail);
        return success();
    }

    /**
     * 获取送货验收信息
     *
     * @param orderDate             订单日期（格式yyyyMMdd）
     * @param distributionCompanyId 配送公司id
     * @return
     */
    @GetMapping("/getInfo/{orderDate}/{distributionCompanyId}")
    public ResponseResult<MOrderCheckDetail> getInfo(@PathVariable("orderDate") Long orderDate, @PathVariable("distributionCompanyId") Long distributionCompanyId) {

        // 校验配送公司是否存在
        MDistributionCompany distributionCompany = distributionCompanyService.getOKById(distributionCompanyId);
        if (Objects.isNull(distributionCompany)) {
            return error(BusinessEnum.DISTRIBUTION_COMPANY_NOT_EXIST);
        }

        // 获取送货验收信息
        MOrderCheckDetail orderCheckDetail = orderCheckDetailService.getOKByOrderDateAndDistributionCompanyId(orderDate, distributionCompanyId);
        return success(orderCheckDetail);
    }

}
