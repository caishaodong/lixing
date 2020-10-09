package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.dto.OrderInfoExportDTO;
import com.shaoxing.lixing.domain.dto.OrderInfoSearchDTO;
import com.shaoxing.lixing.domain.entity.MOrderInfo;
import com.shaoxing.lixing.global.ResponseResult;

import java.util.List;
import java.util.Map;

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
     * 获取订单列表（分页）
     *
     * @param dto
     * @return
     */
    IPage<MOrderInfo> getListPage(OrderInfoSearchDTO dto);

    /**
     * 订单导入信息校验
     *
     * @param orderInfo
     * @return
     */
    ResponseResult dataCheck(MOrderInfo orderInfo);

    /**
     * 获取订单列表（不分页）
     *
     * @param dto
     * @return
     */
    List<MOrderInfo> getList(OrderInfoExportDTO dto);

    /**
     * 数据统计（总价和总数量）
     *
     * @param orderDate
     * @param startOrderDate
     * @param endOrderDate
     * @param distributionCompanyId
     * @return
     */
    Map<String, Object> getTotalMap(Long orderDate, Long startOrderDate, Long endOrderDate, Long distributionCompanyId);
}
