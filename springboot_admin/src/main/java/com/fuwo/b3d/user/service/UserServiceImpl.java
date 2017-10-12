package com.fuwo.b3d.user.service;

import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@CacheConfig(cacheNames = "com.fuwo.b3d.users")
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserInfo get(Integer id) {
        Assert.notNull(id);
        return this.repository.findOne(id);
    }

    @Override
    public UserInfo get(String username) {
        Assert.notNull(username);
        return this.repository.findByUsername(username);
    }

    @Override
    public UserInfo update(UserInfo info) {
        Assert.notNull(info);
        UserInfo entity = this.repository.findByUsername(info.getUsername());
        BeanUtils.copyProperties(info, entity, "id", "profile", "models", "modelPacks");
        if (info.getProfile() != null) {
            BeanUtils.copyProperties(info.getProfile(), entity.getProfile(), "id");
        }
        return this.repository.save(entity);
    }

}
