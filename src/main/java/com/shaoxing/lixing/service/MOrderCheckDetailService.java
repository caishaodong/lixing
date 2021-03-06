package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;

/**
 * <p>
 * 订单送货验收信息表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface MOrderCheckDetailService extends IService<MOrderCheckDetail> {
    MOrderCheckDetail getOKByOrderDateAndDistributionCompanyId(Long orderDate, Long distributionCompanyId);

    /**
     * 根据日期和配送单位id获取title
     *
     * @param orderDate
     * @param distributionCompanyId
     * @return
     */
    String getTitle(Long orderDate, Long distributionCompanyId);
}
