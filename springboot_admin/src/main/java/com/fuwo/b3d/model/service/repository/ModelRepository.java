package com.fuwo.b3d.model.service.repository;

import com.fuwo.b3d.model.model.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ModelRepository extends JpaRepository<Model, Integer> {

    Model findByNoAndPerfect(String modelNo, Model.PerfectEnum perfectEnum);

    Model findByNo(String modelNo);

    @Modifying
    @Query("update Model a set a.discountPrice = ?1  where a.no in ?2")
    Integer updatePrice(Integer price, List<String> nos);

    List<Model> findByNoIn(List<String> nos);

}
