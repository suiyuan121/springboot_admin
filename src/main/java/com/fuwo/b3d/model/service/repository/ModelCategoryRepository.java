package com.fuwo.b3d.model.service.repository;

import com.fuwo.b3d.model.model.ModelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ModelCategoryRepository extends JpaRepository<ModelCategory, Integer> {

    ModelCategory findByItemNoAndCategory_Depth(String itemNo, Integer depth);

    ModelCategory getTopByItemNoOrderByCategoryDesc(String itemNo);
}
