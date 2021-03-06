package com.shaoxing.lixing.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-08-06 18:15
 * @Description
 **/
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    CANCEL(2, "注销"),
    FROZEN(3, "冻结");

    private Integer status;
    private String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
