package com.fuwo.b3d.enums;

public enum TagEnum {

    NEWER("新人"),
    PRIMER("入门"),
    PROFICIENCY("熟练"),
    MASTER("大师"),
    EXPERT("专家")
    //
    ;
    private String desc;

    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    private TagEnum( String desc) {
        this.desc = desc;
    }


}
