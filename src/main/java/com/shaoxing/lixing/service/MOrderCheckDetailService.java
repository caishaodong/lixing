package com.shaoxing.lixing.service;

import com.shaoxing.lixing.domain.entity.MOrderCheckDetail;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
