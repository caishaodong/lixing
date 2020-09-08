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
     * 订单日期（格式yyyyMMdd）
     */
    private Long orderDate;

    public boolean paramCheck() {
        return Objects.nonNull(this.orderDate);
    }
}
