package com.fuwo.b3d.index.enums;

/**
 * Author: zz
 * Date: 2017/8/18  19:04
 */
public enum BannerEnum {
    INDEX(1,"首页"), EXCELLENT(2,"优秀案例"), MODEL(3,"模型案例"), COURSE(4,"教程");

    private int code;

    private String desc;


    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    private BannerEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static BannerEnum getByCode(int code) {
        for (BannerEnum item : BannerEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
