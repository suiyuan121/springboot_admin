package com.fuwo.b3d.user.service;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.user.model.UserModel;
import com.fuwo.b3d.user.service.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@CacheConfig(cacheNames = "com.fuwo.b3d.usermodels")
@Transactional
@Service
public class UserModelServiceImpl implements UserModelService {

    @Autowired
    private UserModelRepository userModelRepository;

    @Override
    public Page<UserModel> findAll(Example<UserModel> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return userModelRepository.findAll(example, pageable);
    }

    @Override
    public List<UserModel> findByUidAndModelId(Integer uid, Integer modelId) {
        Assert.notNull(uid);
        Assert.notNull(modelId);
        return userModelRepository.findByUidAndModelIdAndStatus(uid, modelId, StatusEnum.ENABLE);
    }

    @Override
    public UserModel save(UserModel userModel) {
        Assert.notNull(userModel);
        return userModelRepository.save(userModel);
    }

    @Override
    public Integer getBuys(Integer id) {
        Assert.notNull(id);
        return userModelRepository.countByModelId(id);
    }
}
