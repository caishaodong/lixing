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
     * 客户id（jsonArray字符串）
     */
    private String custormerIds;
    /**
     * 价目id
     */
    private Long priceCategoryId;
    /**
     * 客户id集合
     */
    @Ignore
    private List<Long> custormerIdList;

    public boolean paramCheck() {
        if (StringUtil.isBlank(this.custormerIds) || Objects.isNull(this.priceCategoryId)) {
            return false;
        }
        List<Long> custormerIdList = StringUtil.jsonArrayToLongList(custormerIds);
        if (Objects.isNull(custormerIdList) || custormerIdList.isEmpty()) {
            return false;
        }
        this.custormerIdList = custormerIdList;
        return true;
    }
}
