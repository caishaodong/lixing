package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-07 18:10
 * @Description
 **/
@Data
public class MVarietiesPriceInfoSearchDTO extends PageUtil {

    /**
     * 价目id
     */
    private Long priceCategoryId;
}
