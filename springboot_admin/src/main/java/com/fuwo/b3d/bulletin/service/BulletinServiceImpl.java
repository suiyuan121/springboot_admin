package com.fuwo.b3d.bulletin.service;

import com.fuwo.b3d.bulletin.model.Bulletin;
import com.fuwo.b3d.bulletin.service.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@CacheConfig(cacheNames = "com.fuwo.b3d.bulletins")
@Transactional
@Service
public class BulletinServiceImpl implements BulletinService {

    @Autowired
    BulletinRepository bulletinRepository;

    @Override
    public Page<Bulletin> pageQuery(Example<Bulletin> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return bulletinRepository.findAll(example, pageable);
    }

    @Override
    public void save(Bulletin bulletin) {
        Assert.notNull(bulletin);
        bulletinRepository.save(bulletin);
    }

    @Override
    public Bulletin get(Long id) {
        Assert.notNull(id);
        return bulletinRepository.findOne(id);
    }

    @Override
    public void updateBulletin(Bulletin bulletin) {
        Assert.notNull(bulletin);
        bulletinRepository.saveAndFlush(bulletin);
    }
}
