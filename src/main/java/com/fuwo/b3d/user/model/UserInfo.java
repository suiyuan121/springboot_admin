package com.fuwo.b3d.user.model;


import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "auth_user")
public class UserInfo implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserProfile profile;

    @JoinTable(name = "3d_user_models", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "model_id"))
    @ManyToMany
    private List<Model> models;

    @JoinTable(name = "3d_user_modelpacks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "modelpack_id"))
    @ManyToMany
    private List<ModelPack> modelPacks;


    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<ModelPack> getModelPacks() {
        return modelPacks;
    }

    public void setModelPacks(List<ModelPack> modelPacks) {
        this.modelPacks = modelPacks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
