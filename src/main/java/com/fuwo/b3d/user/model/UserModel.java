package com.fuwo.b3d.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "3d_user_models")
public class UserModel implements Serializable {


    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Column(name = "user_id")
    private Integer uid;

    @JsonIgnore
    @Column(name = "model_id")
    private Integer modelId;

    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private Model model;

    @Column(name = "status", columnDefinition = "int(11) default 1")
    @Where(clause = "status=1")
    private StatusEnum status = StatusEnum.ENABLE;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
