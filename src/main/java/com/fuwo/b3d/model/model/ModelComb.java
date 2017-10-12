package com.fuwo.b3d.model.model;

import com.fuwo.b3d.enums.StatusEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "3d_modelcomb")
@Entity
public class ModelComb implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "design_no")
    private String designNo;

    @Column(name = "style")
    private StyleEnum style;

    @Column(name = "space")
    private SpaceEnum space;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "price")
    private Integer price;

    @Column(name = "name")
    private String name;

    @Column(name = "collects_initial")
    private Integer collectsInitial;

    @Column(name = "collects_increase")
    private Integer collectsIncrease;

    @Column(name = "priority")
    private Integer priority;

    @JoinTable(name = "3d_modelcomb_models", joinColumns = @JoinColumn(name = "comb_id"), inverseJoinColumns = @JoinColumn(name = "model_id"))
    @ManyToMany
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
    private StatusEnum status=StatusEnum.ENABLE;


    public enum StyleEnum {

        XDJY("0", "现代简约"),
        OS("1", "欧式"),
        TY("2", "田园"),
        DZH("3", "地中海"),
        JO("4", "简欧"),
        RS("5", "日式"),
        MS("6", "美式"),
        BO("7", "北欧"),
        HD("8", "混搭"),
        XZS("9", "新中式"),
        JM("10", "简美"),
        MD("11", " 摩登");

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

        private StyleEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }

    public enum SpaceEnum {


        KT("0", "客厅"),

        CF("1", "厨房"),

        CT("2", "餐厅"),

        KCT("3", "客餐厅"),

        ZW("4", "主卧"),

        CW("5", "次卧"),

        XG("6", "玄关"),

        WSJ("7", "卫生间"),

        SF("8", "书房"),

        ETF("9", "儿童房"),

        ZL("10", "走廊"),

        YT("11", "阳台"),

        DGN("12", "多功能室");
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

        private SpaceEnum(String code, String desc) {
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

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public SpaceEnum getSpace() {
        return space;
    }

    public void setSpace(SpaceEnum space) {
        this.space = space;
    }

    public StyleEnum getStyle() {

        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCollectsInitial() {
        return collectsInitial;
    }

    public void setCollectsInitial(Integer collectsInitial) {
        this.collectsInitial = collectsInitial;
    }

    public Integer getCollectsIncrease() {
        return collectsIncrease;
    }

    public void setCollectsIncrease(Integer collectsIncrease) {
        this.collectsIncrease = collectsIncrease;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
