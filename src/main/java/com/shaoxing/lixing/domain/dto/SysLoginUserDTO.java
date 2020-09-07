package com.shaoxing.lixing.domain.dto;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-07 12:31
 * @Description
 **/
@Data
public class SysLoginUserDTO {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;
}
