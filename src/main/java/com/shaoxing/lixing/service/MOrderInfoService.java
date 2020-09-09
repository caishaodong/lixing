package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MOrderInfo;
import com.shaoxing.lixing.global.util.PageUtil;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-08
 */
public interface MOrderInfoService extends IService<MOrderInfo> {

    MOrderInfo getOKById(Long id);

    /**
     * 获取订单列表
     *
     * @param dto
     * @return
     */
    PageUtil<MOrderInfo> getListPage(OrderInfoSearchDTO dto);
}
