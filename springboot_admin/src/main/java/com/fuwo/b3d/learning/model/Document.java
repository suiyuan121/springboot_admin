package com.fuwo.b3d.learning.model;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.enums.TagEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "3d_document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "doc_type")
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String cont;

    @Column(name = "state")
    private StateEnum state;

    @Column(name = "views_initial")
    private Integer viewsInitial;

    @Column(name = "views_increase")
    private Integer viewsIncrease;

    @Column(name = "tag")
    private TagEnum tag;

    @Column(name = "priority")
    private Integer priority;

    //版块
    @Column(name = "subject")
    private SubjectEnum subject;

    @Column(name = "poster")
    private String poster;

    @Column(name = "creator")
    private String creator;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "status")
    @Where(clause = "status=1")
    private StatusEnum status = StatusEnum.ENABLE;

    public enum SubjectEnum {

        HXXG("0", "户型相关"),
        JCSJ("1", "基础设计"),
        QWYZ("2", "全屋硬装"),
        QWDZ("3", "全屋定制"),
        MXSC("4", "模型上传"),
        SJHT("5", "商家后台"),
        LFJC("6", "量房教程"),
        GXSM("7", "更新说明"),
        //
        ;

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

        private SubjectEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Integer getViewsInitial() {
        return viewsInitial;
    }

    public void setViewsInitial(Integer viewsInitial) {
        this.viewsInitial = viewsInitial;
    }

    public Integer getViewsIncrease() {
        return viewsIncrease;
    }

    public void setViewsIncrease(Integer viewsIncrease) {
        this.viewsIncrease = viewsIncrease;
    }

    public TagEnum getTag() {
        return tag;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public SubjectEnum getSubject() {
        return subject;
    }

    public void setSubject(SubjectEnum subject) {
        this.subject = subject;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
}
