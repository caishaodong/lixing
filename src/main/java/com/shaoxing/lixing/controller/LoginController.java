package com.shaoxing.lixing.controller;

import com.shaoxing.lixing.domain.dto.SysLoginUserDTO;
import com.shaoxing.lixing.domain.entity.SysUser;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.global.enums.BusinessEnum;
import com.shaoxing.lixing.global.enums.UserStatusEnum;
import com.shaoxing.lixing.global.util.StringUtil;
import com.shaoxing.lixing.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-07 12:09
 * @Description 登入登出
 **/
@RestController
@RequestMapping("/sysUser")
public class LoginController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResponseResult<SysUser> login(@RequestBody SysLoginUserDTO dto) {
        if (StringUtil.isBlank(dto.getMobile()) || StringUtil.isBlank(dto.getPassword())) {
            return error(BusinessEnum.PARAM_ERROR);
        }
        SysUser loginUser = sysUserService.getByMobile(dto.getMobile());
        if (Objects.isNull(loginUser)) {
            return error(BusinessEnum.USER_NOT_EXIST);
        }
        if (UserStatusEnum.CANCEL.getStatus().equals(loginUser.getStatus())) {
            return error(BusinessEnum.USER_CANCEL);
        } else if (UserStatusEnum.FROZEN.getStatus().equals(loginUser.getStatus())) {
            return error(BusinessEnum.USER_FROZEN);
        }

        // 密码校验
        if (!StringUtil.equals(dto.getPassword(), loginUser.getPassword())) {
            error(BusinessEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }

        return success(loginUser);
    }
}
