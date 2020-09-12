package com.shaoxing.lixing.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-09-12 21:08
 * @Description
 **/
@Data
public class DistributionCompanyExportVO {
    /**
     * 配送公司id
     */
    private Long distributionCompanyId;

    /**
     * 配送单位名称
     */
    private String distributionCompanyName;

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
     * 是否需要开机打发票（0：不需要，1：需要）
     */
    private Integer needInvoice;
    /**
     * 是否需要开机打发票（0：不需要，1：需要）
     */
    private String needInvoiceStr;
    /**
     * 备注
     */
    private String remark;
    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 所属价目表id
     */
    private Long priceCategoryId;
    /**
     * 所属价目表名称
     */
    private String priceCategoryName;
    /**
     * 商品类别id
     */
    private Long varietiesPriceId;
    /**
     * 商品类别名称
     */
    private String varietiesPriceName;
}
