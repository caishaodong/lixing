package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.StringUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-07 17:45
 * @Description
 **/
@Data
public class MVarietiesPriceInfoDTO {

    /**
     * id（修改时传入）
     */
    Long id;
    /**
     * 价目id
     */
    private Long priceCategoryId;

    /**
     * 食材品种名称
     */
    private String name;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 备注
     */
    private String remark;

    public boolean saveParamCheck() {
        return Objects.nonNull(this.priceCategoryId) && StringUtil.isNotBlank(this.name) && StringUtil.isNotBlank(this.unit)
                && Objects.nonNull(this.price) && (price.compareTo(BigDecimal.ZERO) > 0);
    }

    public boolean updateParamCheck() {
        return Objects.nonNull(this.id) && Objects.nonNull(this.priceCategoryId) && StringUtil.isNotBlank(this.name) && StringUtil.isNotBlank(this.unit)
                && Objects.nonNull(this.price) && (price.compareTo(BigDecimal.ZERO) > 0);
    }
}
