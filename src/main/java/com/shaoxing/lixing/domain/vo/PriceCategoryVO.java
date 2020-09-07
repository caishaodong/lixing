package com.shaoxing.lixing.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-09-07 20:01
 * @Description
 **/
@Data
public class PriceCategoryVO {
    /**
     * 所属价目表id
     */
    private Long id;
    /**
     * 所属价目表名称
     */
    private String name;
    /**
     * 价目详情
     */
    private List<VarietiesPriceInfoVO> varietiesPriceInfoVOList;
}
