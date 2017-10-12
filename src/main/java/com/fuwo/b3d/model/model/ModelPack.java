package com.fuwo.b3d.model.model;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "3d_modelpack")
@Entity
public class ModelPack implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "name")
    private String name;

    @Column(name = "buys_initial")
    private Integer buysInitial;

    @Column(name = "buys_increase")
    private Integer buysIncrease;

    @Column(name = "price")
    private Integer price;

    @Column(name = "type")
    private TypeEnum type;

    @Column(name = "state")
    private StateEnum state;


    @JoinTable(name = "3d_modelpack_models", joinColumns = @JoinColumn(name = "pack_id"), inverseJoinColumns = @JoinColumn(name = "model_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Model> models;

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

    public enum TypeEnum {

        GZH("0", "工装"),
        SF("1", "沙发"),
        CH("2", "床"),
        GZ("3", "柜子"),
        PS("4", "配饰"),
        CZY("5", "餐座椅"),
        DS("6", "灯饰"),
        CJ("7", "茶几"),
        ZHDP("8", "组合搭配"),
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

        private TypeEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;

        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuysInitial() {
        return buysInitial;
    }

    public void setBuysInitial(Integer buysInitial) {
        this.buysInitial = buysInitial;
    }

    public Integer getBuysIncrease() {
        return buysIncrease;
    }

    public void setBuysIncrease(Integer buysIncrease) {
        this.buysIncrease = buysIncrease;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }


    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
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
}
