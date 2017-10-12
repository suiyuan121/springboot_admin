package com.fuwo.b3d.sampleroom.model;

import com.fuwo.b3d.enums.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_sampleroom")
@Entity
public class SampleRoom implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "brand")
    private String brand;

    //预览图
    @Column(name = "prev_url")
    private String prevUrl;

    @Column(name = "title")
    private String title;

    //全景图
    @Column(name = "pano_url")
    private String panoUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "creator")
    private String creator;

    @Column(name = "created")
    private Date created;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "status")
    private StatusEnum status;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPanoUrl() {
        return panoUrl;
    }

    public void setPanoUrl(String panoUrl) {
        this.panoUrl = panoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
