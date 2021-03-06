package com.shaoxing.lixing.domain.dto;

import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-11 14:21
 * @Description
 **/
@Data
public class OrderInfoExportDTO {
    /**
     * 订单开始日期（格式yyyyMMdd）
     */
    private Long startOrderDate;
    /**
     * 订单结束日期（格式yyyyMMdd）
     */
    private Long endOrderDate;
    /**
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;
    /**
     * 配送单位id
     */
    private Long distributionCompanyId;

    public boolean paramCheck() {
        return (Objects.nonNull(this.orderDate) || (Objects.nonNull(startOrderDate) || Objects.nonNull(endOrderDate))) && Objects.nonNull(this.distributionCompanyId);
    }
}
