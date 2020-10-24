package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.StringUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-07 18:20
 * @Description
 **/
@Data
public class CustomerBindingPriceCategoryDTO {
    /**
     * 客户id（jsonArray字符串，如：[1,2,3,4]）
     */
    private String customerIds;
    /**
     * 价目id
     */
    private Long priceCategoryId;
    /**
     * 客户id集合
     */
    @Ignore
    private List<Long> customerIdList;

    public boolean paramCheck() {
        if (StringUtil.isBlank(this.customerIds) || Objects.isNull(this.priceCategoryId)) {
            return false;
        }
        List<Long> customerIdList = StringUtil.jsonArrayToLongList(customerIds);
        this.customerIdList = customerIdList;
        return true;
    }
}
