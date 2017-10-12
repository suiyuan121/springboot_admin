package com.fuwo.b3d.enums;

public enum StatusEnum {

    DISABLE("0", "无效"),
    ENABLE("1", "有效"),
    UBDEFINED1("2", "未定义"),
    UBDEFINED2("3", "未定义"),
    UBDEFINED3("4", "未定义"),
    UBDEFINED4("5", "未定义"),
    UBDEFINED5("6", "未定义"),
    UBDEFINED6("7", "未定义"),
    UBDEFINED7("8", "未定义"),

    //用于之前python的state状态
    DELETE("9", "已删除"),
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

    private StatusEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }


}
