package com.shaoxing.lixing.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:33
 * @Description
 **/
public enum BusinessEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    PARAM_ERROR(400, "参数错误"),

    USER_NOT_EXIST(101, "用户不存在"),
    MOBILE_EXIST(102, "该手机号已存在"),
    LOGIN_NAME_OR_PASSWORD_ERROR(103, "用户名或密码错误"),
    LOGIN_NAME_IS_EMPTY(104, "用户名不能为空"),
    PASSWORD_IS_EMPTY(105, "密码不能为空"),
    USER_CANCEL(106, "该账号已注销"),
    USER_FROZEN(107, "该账号已冻结"),

    CUSTOMER_INFO_ERROR(201, "客户信息有误"),

    PRICE_CATEGORY_INFO_ERROR(301, "价目信息有误"),
    ;
    private Integer code;
    private String desc;

    BusinessEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
