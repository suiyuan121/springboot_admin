package com.fuwo.b3d.enums;

public enum StateEnum {

    PRIVATE("0", "不公开"),
    PUBLIC("1", "公开"),
    //
    ;
    private String desc;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    private StateEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;

    }


}
