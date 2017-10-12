package com.fuwo.b3d.learning.service.repository;

import com.fuwo.b3d.learning.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findAllByCategory(@Param(value = "category") Video.CategoryEnum category, Pageable pageable);
}
