package com.fuwo.b3d.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "3d_user_modelpacks")
public class UserModelPack implements Serializable {


    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Column(name = "user_id")
    private Integer uid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserInfo userInfo;

    @JsonIgnore
    @Column(name = "modelpack_id")
    private Long modelPackId;

    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "modelpack_id", insertable = false, updatable = false)
    private ModelPack modelPack;

    @Column(name = "status", columnDefinition = "int(11) default 1")
    @Where(clause = "status=1")
    private StatusEnum status = StatusEnum.ENABLE;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getModelPackId() {
        return modelPackId;
    }

    public void setModelPackId(Long modelPackId) {
        this.modelPackId = modelPackId;
    }

    public ModelPack getModelPack() {
        return modelPack;
    }

    public void setModelPack(ModelPack modelPack) {
        this.modelPack = modelPack;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
