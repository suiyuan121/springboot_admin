package com.fuwo.b3d.user.service.repository;

import com.fuwo.b3d.user.model.UserModelPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserModelPackRepository extends JpaRepository<UserModelPack, Long> {


}
