package com.shaoxing.lixing.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:06
 * @Description
 **/
@Data
public class OrderInfoDTO {
    /**
     * 订单id（修改时传入）
     */
    private Long id;
    /**
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;

    /**
     * 配送单位id
     */
    private Long distributionCompanyId;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 品种价格id
     */
    private Long varietiesPriceId;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 备注
     */
    private String remark;

    public boolean paramCheck() {
        return Objects.nonNull(orderDate) && Objects.nonNull(distributionCompanyId) && Objects.nonNull(customerId)
                && Objects.nonNull(varietiesPriceId) && Objects.nonNull(num);
    }
}
