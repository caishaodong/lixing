package com.shaoxing.lixing.domain.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-11 17:52
 * @Description
 **/
@Data
public class DistributionCompanyExportVO {
    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 配送明细
     */
    private String detail;
}
