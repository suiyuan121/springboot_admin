package com.fuwo.b3d.designcase.model;

import com.fuwo.b3d.enums.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_case_img")
@Entity
public class CaseImg implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="case_id")
    private Long  caseId;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "creator")
    private String creator;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "status")
    private StatusEnum status = StatusEnum.ENABLE;

    public final static String  HOUSE="house";
    public final static String  EFFECT="effect";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
}
