package com.shaoxing.lixing.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:43
 * @Description
 **/
public enum OrderTypeEnum {
    TODAY(1, "今日订单"),
    PAST(2, "历史订单"),
    FUTURE(3, "暂存订单");

    private Integer type;
    private String desc;

    OrderTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
