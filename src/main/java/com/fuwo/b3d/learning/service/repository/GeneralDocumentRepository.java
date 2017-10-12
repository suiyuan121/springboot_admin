package com.fuwo.b3d.learning.service.repository;

import com.fuwo.b3d.learning.model.GeneralDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface GeneralDocumentRepository extends JpaRepository<GeneralDocument, Long> {

    @RestResource(path = "findBySubjectOrderByViews", rel = "findBySubjectOrderByViews")
    @Query("from GeneralDocument d where status=1 and state=1 and subject=?1  order by  viewsIncrease desc ")
    Page<GeneralDocument> findBySubjectOrderByViews(@Param(value = "subject") GeneralDocument.SubjectEnum subject, Pageable pageable);

}
