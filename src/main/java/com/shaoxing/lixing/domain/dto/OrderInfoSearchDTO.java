package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:39
 * @Description
 **/
@Data
public class OrderInfoSearchDTO extends PageUtil {
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
