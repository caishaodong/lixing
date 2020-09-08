package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:57
 * @Description
 **/
@Data
public class OrderCheckDetailDTO {
    /**
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;
    /**
     * 送货人姓名
     */
    private String deliveryUserName;

    /**
     * 验收人姓名
     */
    private String checkUserName;

    public boolean paramCheck() {
        return Objects.nonNull(orderDate) && StringUtil.isNotBlank(deliveryUserName) && StringUtil.isNotBlank(checkUserName);
    }
}
