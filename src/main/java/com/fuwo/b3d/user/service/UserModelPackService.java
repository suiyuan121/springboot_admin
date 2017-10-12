package com.fuwo.b3d.user.service;

import com.fuwo.b3d.user.model.UserModelPack;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserModelPackService {

    Page<UserModelPack> findAll(Example<UserModelPack> example, Pageable pageable);


}
