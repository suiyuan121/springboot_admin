package com.fuwo.b3d.user.service;

import com.fuwo.b3d.user.model.UserModelPack;
import com.fuwo.b3d.user.service.repository.UserModelPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@CacheConfig(cacheNames = "com.fuwo.b3d.usermodelpacks")
@Transactional
@Service
public class UserModelPackServiceImpl implements UserModelPackService {

    @Autowired
    private UserModelPackRepository userModelPackRepository;

    @Override
    public Page<UserModelPack> findAll(Example<UserModelPack> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return userModelPackRepository.findAll(example, pageable);
    }

}