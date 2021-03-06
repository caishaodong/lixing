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
    CUSTOMER_NAME_EMPTY(202, "客户姓名不能为空"),
    CUSTOMER_NAME_REPEAT(203, "客户姓名不能重复"),
    CUSTOMER_NOT_EXIST(204, "客户不存在"),
    CUSTOMER__NAME_NOT_EXIST(205, "客户名称不存在"),

    PRICE_CATEGORY_INFO_ERROR(301, "价目信息有误"),
    VARIETIES_PRICE_NOT_EXIST(302, "品种不存在"),
    PRICE_CATEGORY_NOT_EXIST(303, "价目不存在"),
    VARIETIES_PRICE_NAME_REPEAT(304, "品种名称不能重复"),
    PRICE_CATEGORY_NAME_NOT_EXIST(305, "价目名称不存在"),
    VARIETIES_PRICE_NAME_NOT_EXIST(306, "品种名称不存在"),

    DISTRIBUTION_COMPANY_NOT_EXIST(401, "配送公司不存在"),
    DISTRIBUTION_COMPANY_NAME_REPEAT(402, "配送公司名称不能重复"),
    DISTRIBUTION_COMPANY_NAME_NOT_EXIST(403, "配送公司名称不存在"),

    ORDER_NOT_EXIST(501, "订单不存在"),

    COMPANY_BILL_NOT_EXIST(601, "账单不存在"),
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
