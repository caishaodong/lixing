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
     * 价目id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String priceCategoryIds;
    /**
     * 品种价格id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String varietiesPriceIds;
    /**
     * 订单开始日期（格式yyyyMMdd）
     */
    private Long startOrderDate;
    /**
     * 订单结束日期（格式yyyyMMdd）
     */
    private Long endOrderDate;
}
