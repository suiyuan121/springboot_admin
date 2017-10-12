package com.fuwo.b3d.checkin.service.repository;

import com.fuwo.b3d.checkin.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    //查询昨天前一天的签到情况
    @Query(nativeQuery = true, value = "SELECT * FROM 3d_checkin " +
            "WHERE DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -1 DAY),'%Y%d%d')=DATE_FORMAT(TIME,'%Y%d%d') " +
            "and uid=?1 LIMIT 1")
    Checkin findLastByUid(Integer uid);

    //查询当天的签到情况
    @Query(nativeQuery = true, value = "SELECT * FROM 3d_checkin " +
            "WHERE DATE_FORMAT(NOW(),'%Y%d%d')=DATE_FORMAT(TIME,'%Y%d%d') and uid=?1 LIMIT 1")
    Checkin findCurrentByUid(Integer uid);

}
