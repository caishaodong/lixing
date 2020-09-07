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
 * 配送公司信息表
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MDistributionCompany extends Model<MDistributionCompany> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配送单位名称
     */
    private String name;

    /**
     * 是否需要开机打发票（0：不需要，1：需要）
     */
    private Integer needInvoice;

    /**
     * 结算扣率
     */
    private BigDecimal settlementDeductionRate;

    /**
     * 地区编码
     */
    private Integer areaCode;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系人姓名
     */
    private String contactUserName;

    /**
     * 联系人手机号
     */
    private String contactUserMobile;

    /**
     * 订单管理人姓名
     */
    private String orderManagerName;

    /**
     * 订单管理人手机号
     */
    private String orderManagerMobile;

    /**
     * 财务联系人姓名
     */
    private String financialContactName;

    /**
     * 财务联系人手机号
     */
    private String financialContactMobile;

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
