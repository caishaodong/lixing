package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:54
 * @Description
 **/
@Data
public class IndexStatisticsVO<T> extends PageUtil<T> {
    /**
     * 总价
     */
    private BigDecimal totalAmount;
}
