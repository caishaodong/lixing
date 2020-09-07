package com.shaoxing.lixing.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-07 19:29
 * @Description
 **/
@Data
public class DistributionCompanyDTO {

    /**
     * 配送公司id（修改时传入）
     */
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
}
