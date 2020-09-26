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
 * 公司账单表
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FCompanyBill extends Model<FCompanyBill> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 费用类别名称
     */
    private String feeCategoryName;

    /**
     * 费用科目名称
     */
    private String feeSubjectName;

    /**
     * 进货产地编码
     */
    private Integer areaCode;

    /**
     * 进货产地名称
     */
    private String areaName;

    /**
     * 进货产地详细地址
     */
    private String address;

    /**
     * 账单日期
     */
    private Long billDate;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 录入人
     */
    private String enteredUserName;

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
