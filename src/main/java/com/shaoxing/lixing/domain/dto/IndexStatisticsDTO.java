package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:54
 * @Description
 **/
@Data
public class IndexStatisticsDTO extends PageUtil {
    /**
     * 客户id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String customerIds;
    /**
     * 品种价格id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String varietiesPriceIds;
    /**
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;
    /**
     * 总价
     */
    private BigDecimal totalAmount;
}
