package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.domain.entity.MCustomerInfo;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-07 19:59
 * @Description
 **/
@Data
public class CustomerInfoListVO extends MCustomerInfo {
    /**
     * 是否绑定价目（0未绑定，1已绑定）
     */
    private Integer isBandPriceCategory;
}
