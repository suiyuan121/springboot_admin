package com.fuwo.b3d.designcase.service.repository;

import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CaseRepository extends JpaRepository<Case, Long> {

    List<Case>  findByIdIn(List<Long> ids);
    Case findById(Long id);
    Case findByIdAndStatus(Long id ,StatusEnum  statusEnum);
    List<Case>  findByIdNotInAndDesignStyle(Long id,Case.DesignStyleEnum designStyleEnum);
    List<Case>  findTop4ByIdNotInAndDesignStyle(Long id,Case.DesignStyleEnum designStyleEnum);
}
