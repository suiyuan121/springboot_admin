package com.fuwo.b3d.learning.service.repository;

import com.fuwo.b3d.learning.model.BeginnerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BeginnerDocumentRepository extends JpaRepository<BeginnerDocument, Long> {


}
