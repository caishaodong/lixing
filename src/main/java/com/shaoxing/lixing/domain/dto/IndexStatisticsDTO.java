package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import io.github.yedaxia.apidocs.Ignore;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:54
 * @Description
 **/
@Data
public class IndexStatisticsDTO extends PageUtil {
    /**
     * 配送公司id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String distributionCompanyIds;
    /**
     * 客户id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String customerIds;
    /**
     * 价目id（jsonArray字符串，如：[1,2,3,4]）
     */
    @Ignore
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
    /**
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;
}
