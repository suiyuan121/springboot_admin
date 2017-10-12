package com.fuwo.b3d.checkin.service;

import com.fuwo.b3d.checkin.model.Checkin;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface CheckinService {


    void save(Checkin checkin);

    Checkin get(Long id);

    boolean checkin(Integer uid);

    List<Checkin> findByDate(Integer uid, Date startDate, Date endDate);


    Checkin findCurrentByUid(Integer uid);
}
