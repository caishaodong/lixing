package com.shaoxing.lixing.domain.dto;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-08 16:14
 * @Description
 **/
@Data
public class CustomerInfoDTO {
    /**
     * 客户id（解绑客户和配送公司关系时传入）
     */
    private Long customerId;
    /**
     * 客户名称（绑定客户和配送公司关系时传入）
     */
    private String name;
    /**
     * 配送公司id
     */
    private Long distributionCompanyId;
}
