package com.fuwo.b3d.order.model;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.user.model.UserInfo;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "3d_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private UserInfo userInfo;

    @Column(name = "uid")
    private Integer uid;

    //订单号
    @Column(name = "no")
    private String no;

    //价格，福币数
    @Column(name = "amount")
    private Integer amount;

    //商品，枚举
    @Column(name = "dealType")
    private DealTypeEnum dealType;

    //
    @Column(name = "state")
    private OrderStatusEnum state;

    //取消的理由
    @Column(name = "cancelledReason")
    private String cancelledReason;

    @Column(name = "creator")
    private String creator;

    @Column(name = "created")
    private Date created;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "status")
    @Where(clause = "status=1")
    private StatusEnum status = StatusEnum.ENABLE;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public DealTypeEnum getDealType() {
        return dealType;
    }

    public void setDealType(DealTypeEnum dealType) {
        this.dealType = dealType;
    }

    public OrderStatusEnum getState() {
        return state;
    }

    public void setState(OrderStatusEnum state) {
        this.state = state;
    }

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public enum OrderStatusEnum {


        SUCCESS("0", "已付款"),

        FAIL("1", "未付款");


        private String desc;
        private String code;

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private OrderStatusEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }

    public enum DealTypeEnum {

        MODEL("0", "9", "模型使用权购买"),

        MODELPACK("1", "9", "模型包使用购买");


        private String desc;
        //3d 存数据库的值
        private String code;
        //支付系统的类型
        private String oldCode;

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOldCode() {
            return oldCode;
        }

        public void setOldCode(String oldCode) {
            this.oldCode = oldCode;
        }

        private DealTypeEnum(String code, String oldCode, String desc) {
            this.desc = desc;
            this.code = code;
            this.oldCode = oldCode;
        }

    }
}
