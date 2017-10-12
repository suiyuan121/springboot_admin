package com.fuwo.b3d.checkin.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "3d_checkin")
@Entity
public class Checkin implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "uid")
    private Integer uid;


    @Column(name = "time")
    private Date time;

    //连续签到数，
    @Column(name = "count")
    private Integer count;


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
