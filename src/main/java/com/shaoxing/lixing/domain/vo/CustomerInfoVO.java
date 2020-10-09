package com.shaoxing.lixing.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-09-07 19:59
 * @Description
 **/
@Data
public class CustomerInfoVO {
    /**
     * 客户id
     */
    private Long id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 客户价目信息
     */
    private List<PriceCategoryVO> priceCategoryVOList;
}
