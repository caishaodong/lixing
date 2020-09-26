package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.StringUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-09 12:16
 * @Description
 **/
@Data
public class FCompanyBillDTO {
    /**
     * 账单id（修改时传入）
     */
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

    public boolean paramCheck() {
        return StringUtil.isNotBlank(feeCategoryName) && StringUtil.isNotBlank(feeSubjectName) && StringUtil.isNotBlank(address) && Objects.nonNull(billDate)
                && Objects.nonNull(price) && Objects.nonNull(num) && Objects.nonNull(weight) && Objects.nonNull(totalPrice) && StringUtil.isNotBlank(enteredUserName);
    }
}
