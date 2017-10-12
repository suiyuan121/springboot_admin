package com.fuwo.b3d.sampleroom.service;

import com.fuwo.b3d.sampleroom.model.SampleRoom;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SampleRoomService {

    Page<SampleRoom> findAll(Example<SampleRoom> example, Pageable pageable);

    void save(SampleRoom sampleRoom);

    SampleRoom get(Long id);

    Integer countAll();

}
