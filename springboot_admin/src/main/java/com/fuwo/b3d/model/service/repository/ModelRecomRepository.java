package com.fuwo.b3d.model.service.repository;

import com.fuwo.b3d.model.model.ModelRecom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ModelRecomRepository extends JpaRepository<ModelRecom, Integer> {

}
