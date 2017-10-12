package com.fuwo.b3d.index.model;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.index.enums.BannerEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_banner")
@Entity
public class Banner implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "type")
    private BannerEnum type;

    @Column(name = "file_url")
    private String filePath;

    @Column(name = "creator")
    private String creator;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;


    @Column(name = "status", columnDefinition = "int default 0", nullable = false)
    @Where(clause = "status=1")
    private StatusEnum status;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public BannerEnum getType() {
        return type;
    }

    public void setType(BannerEnum type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public enum BannerEnum {
        INDEX(0, "首页"),

        EXCELLENT(1, "优秀案例"),

        MODEL(2, "模型案例"),

        COURSE(3, "教程");

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


    }
}
