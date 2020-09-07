package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.SysUser;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.mapper.SysUserMapper;
import com.shaoxing.lixing.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser getByMobile(String mobile) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, mobile)
                .eq(SysUser::getIsDeleted, YesNoEnum.NO));
    }
}
