package com.shaoxing.lixing.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:48
 * @Description
 **/
@Data
public class IndexDataVo {
    /**
     * 今日的出货量
     */
    private BigDecimal todayNum;
    /**
     * 昨日的出货量
     */
    private BigDecimal yesterdayNum;
    /**
     * 今日的营业额
     */
    private BigDecimal todayAmount;
    /**
     * 昨日的营业额
     */
    private BigDecimal yesterdayAmount;
    /**
     * 本周营业额
     */
    private BigDecimal thisWeekAmount;
    /**
     * 上周营业额
     */
    private BigDecimal lastWeekAmount;
}
