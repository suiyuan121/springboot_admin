package com.fuwo.b3d.user.service;

import com.fuwo.b3d.user.model.UserModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserModelService {

    Page<UserModel> findAll(Example<UserModel> example, Pageable pageable);


    List<UserModel> findByUidAndModelId(Integer uid, Integer modelId);


    UserModel save(UserModel userModel);

    Integer getBuys(Integer id);


}
