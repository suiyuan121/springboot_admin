package com.fuwo.b3d.index.service.repository;

import com.fuwo.b3d.index.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findByStatusAndTypeOrderBySeqAsc(int status,int type);
}
