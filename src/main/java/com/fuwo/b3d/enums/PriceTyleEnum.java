package com.fuwo.b3d.enums;

public enum PriceTyleEnum {

    FREE("0", "免费"),
    FCOIN("1", "福币"),
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

    private PriceTyleEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;

    }


}
