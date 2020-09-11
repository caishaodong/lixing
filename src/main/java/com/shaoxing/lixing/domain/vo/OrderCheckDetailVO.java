package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-11 15:19
 * @Description
 **/
@Data
public class OrderCheckDetailVO extends MOrderCheckDetail {
    /**
     * 总价
     */
    private BigDecimal totalMoney;
}
