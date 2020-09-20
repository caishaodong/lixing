package com.shaoxing.lixing.global.enums;

public enum OrderSourceEnum {
    ADMIN(1, "后台添加"),
    COPY(2, "复制"),
    IMPORT(3, "导入");

    private Integer source;
    private String desc;

    OrderSourceEnum(Integer source, String desc) {
        this.source = source;
        this.desc = desc;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
