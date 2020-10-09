package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:39
 * @Description
 **/
@Data
public class OrderInfoSearchVO<T> extends PageUtil<T> {
    /**
     * 标题
     */
    private String title;
    /**
     * 总价
     */
    private BigDecimal totalAmount;
    /**
     * 总数量
     */
    private BigDecimal totalNum;

}
