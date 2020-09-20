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
 * 订单表
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MOrderInfo extends Model<MOrderInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单日期
     */
    private Long orderDate;

    /**
     * 配送单位id
     */
    private Long distributionCompanyId;

    /**
     * 配送单位名称
     */
    private String distributionCompanyName;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 价目id
     */
    private Long priceCategoryId;

    /**
     * 价目名称
     */
    private String priceCategoryName;

    /**
     * 品种价格id
     */
    private Long varietiesPriceId;

    /**
     * 品种名称
     */
    private String varietiesName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 来源（1：后台添加，2：复制，3：导入）
     */
    private Integer source;

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
