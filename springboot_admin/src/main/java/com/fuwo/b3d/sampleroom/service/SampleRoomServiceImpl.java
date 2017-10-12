package com.fuwo.b3d.sampleroom.service;

import com.fuwo.b3d.sampleroom.model.SampleRoom;
import com.fuwo.b3d.sampleroom.service.repository.SampleRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@CacheConfig(cacheNames = "com.fuwo.b3d.samplerooms")
@Transactional
@Service
public class SampleRoomServiceImpl implements SampleRoomService {

    @Autowired
    private SampleRoomRepository sampleRoomRepository;

    @Override
    public void save(SampleRoom sampleRoom) {
        Assert.notNull(sampleRoom);
        sampleRoomRepository.save(sampleRoom);
    }


    @Override
    public SampleRoom get(Long id) {
        Assert.notNull(id);
        SampleRoom sampleRoom = sampleRoomRepository.findOne(id);
        return sampleRoom;
    }

    @Override
    public Page<SampleRoom> findAll(Example<SampleRoom> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return sampleRoomRepository.findAll(example, pageable);
    }

    @Override
    public Integer countAll() {
        return null;
    }
}
