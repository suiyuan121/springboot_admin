package com.fuwo.b3d.model.service.repository;

import com.fuwo.b3d.model.model.ModelPack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ModelPackRepository extends JpaRepository<ModelPack, Long> {


}
