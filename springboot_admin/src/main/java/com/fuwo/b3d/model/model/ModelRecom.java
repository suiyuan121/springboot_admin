package com.fuwo.b3d.model.model;

import com.fuwo.b3d.enums.StatusEnum;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_model_recom")
@Entity
public class ModelRecom implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

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

    @Column(name = "buys")
    private Integer buys;

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

    @ManyToOne
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private Model model;

    @Ignore
    @Column(name = "model_id")
    private Integer modelId;


    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBuys() {
        return buys;
    }

    public void setBuys(Integer buys) {
        this.buys = buys;
    }
}
