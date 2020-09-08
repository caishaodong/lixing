package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.domain.entity.MOrderInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-08 18:11
 * @Description
 **/
@Data
public class OrderInfoVO extends MOrderInfo {
    /**
     * 送货人姓名
     */
    private String deliveryUserName;
    /**
     * 验收人姓名
     */
    private String checkUserName;
    /**
     * 订单数量
     */
    private Integer orderCount;
    /**
     * 总价
     */
    private BigDecimal priceCount;
}
