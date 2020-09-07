package com.shaoxing.lixing.service;

import com.shaoxing.lixing.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    SysUser getByMobile(String mobile);
}
