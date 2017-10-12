package com.fuwo.b3d.learning.model;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.enums.TagEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_video")
@Entity
public class Video implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "head_img")
    private String headImg;

    //主讲人
    @Column(name = "speaker")
    private String speaker;

    @Column(name = "plays_initial")
    private Integer playsInitial;

    @Column(name = "plays_increase")
    private Integer playsIncrease;

    @Column(name = "state")
    private StateEnum state;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "category")
    private CategoryEnum category;

    @Column(name = "tag")
    private TagEnum tag;

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

    public enum CategoryEnum {


        HXGJ("0", "户型工具"),

        JCSJ("1", "基础设计"),

        XRDG("2", "渲染打光"),

        QWYZ("3", "全屋硬装工具"),

        QWDZ("4", "全屋定制工具"),

        SJHT("5", "商家后台"),

        MXSC("6", "模型上传"),

        LFJC("7", "量房教程"),
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

        private CategoryEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;

        }

    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public Integer getPlaysInitial() {
        return playsInitial;
    }

    public void setPlaysInitial(Integer playsInitial) {
        this.playsInitial = playsInitial;
    }

    public Integer getPlaysIncrease() {
        return playsIncrease;
    }

    public void setPlaysIncrease(Integer playsIncrease) {
        this.playsIncrease = playsIncrease;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public TagEnum getTag() {
        return tag;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
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
