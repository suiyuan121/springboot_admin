package com.fuwo.b3d.sampleroom.service.repository;

import com.fuwo.b3d.sampleroom.model.SampleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SampleRoomRepository extends JpaRepository<SampleRoom, Long> {


}
