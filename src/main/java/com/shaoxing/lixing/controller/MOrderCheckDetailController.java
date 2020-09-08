package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.dto.OrderCheckDetailDTO;
import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.util.ReflectUtil;
import com.shaoxing.lixing.service.MOrderCheckDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * 订单送货验收信息管理
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/orderCheckDetail")
public class MOrderCheckDetailController extends BaseController {
    @Autowired
    private MOrderCheckDetailService orderCheckDetailService;

    /**
     * 保存或修改订单送货验收信息
     *
     * @param dto
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdate(OrderCheckDetailDTO dto) {
        if (!dto.paramCheck()) {
            return error(BusinessEnum.PARAM_ERROR);
        }

        MOrderCheckDetail existsOrderCheckDetail = orderCheckDetailService.getOKByOrderDate(dto.getOrderDate());
        if (Objects.isNull(existsOrderCheckDetail)) {
            // 新增
            existsOrderCheckDetail = new MOrderCheckDetail();
            BeanUtils.copyProperties(dto, existsOrderCheckDetail);
            ReflectUtil.setCreateInfo(existsOrderCheckDetail, MOrderCheckDetail.class);
        } else {
            // 修改
            existsOrderCheckDetail.setDeliveryUserName(dto.getDeliveryUserName());
            existsOrderCheckDetail.setCheckUserName(dto.getCheckUserName());
        }
        orderCheckDetailService.saveOrUpdate(existsOrderCheckDetail);
        return success();
    }

}
