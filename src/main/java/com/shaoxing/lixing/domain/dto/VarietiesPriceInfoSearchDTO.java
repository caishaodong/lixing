package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-07 18:10
 * @Description
 **/
@Data
public class VarietiesPriceInfoSearchDTO extends PageUtil {

    /**
     * 价目id
     */
    private Long priceCategoryId;

    public boolean paramCheck() {
        return Objects.nonNull(this.priceCategoryId);
    }
}
