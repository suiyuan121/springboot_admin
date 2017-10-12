package com.fuwo.b3d.common;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/26.
 */
public class RestResult {

    private String code;

    private String msg;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public enum ResultCodeEnum {

        SUCC("10000", "成功"),

        ERROR("10001", "未知错误"),

        PARA_ERR("1002", "表单参数错误"),

        NOT_EXIST_ERR("10003", "记录不存在"),

        EMPTY_FILE("20001", "文件为空"),

        DUPLICATE_REQUEST("20002", "重复的请求"),

        GOODS_NOT_EXISTS("20003", "商品不存在"),

        ORDER_PAYED("20004", "订单已付款"),

        USER_NOT_EXISTS("20005", "用户不存在")


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

        private ResultCodeEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }
}
