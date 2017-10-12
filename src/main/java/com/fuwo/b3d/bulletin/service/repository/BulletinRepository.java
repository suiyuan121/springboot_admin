package com.fuwo.b3d.bulletin.service.repository;

import com.fuwo.b3d.bulletin.model.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = Bulletin.class)
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

}
