package com.fuwo.b3d.model.service.repository;

import com.fuwo.b3d.model.model.ModelComb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import java.util.UUID;

@RepositoryRestResource(excerptProjection = ModelComb.class)
public interface ModelCombRepository extends JpaRepository<ModelComb, Long> {

}