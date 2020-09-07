package com.shaoxing.lixing.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品种价格信息表
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MVarietiesPriceInfo extends Model<MVarietiesPriceInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 是否删除（0：未删除，1：删除）
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
