package com.fuwo.b3d.designcase.service.repository;

import com.fuwo.b3d.designcase.model.CaseImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CaseImgResposity extends  JpaRepository<CaseImg, Long> {

    CaseImg findByCaseId(Long caseId);
    List<CaseImg>  findAllByCaseId(Long caseId);
    CaseImg findByCaseIdAndType(Long caseId,String type);
    List<CaseImg> findAllByCaseIdAndType(Long caseId,String type);
   void deleteAllByCaseIdAndType(Long caseId,String type);
}
