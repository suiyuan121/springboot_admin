package com.fuwo.b3d.user.service;

import com.fuwo.b3d.user.model.UserInfo;

public interface UserService {

    UserInfo get(Integer id);

    UserInfo get(String username);

    UserInfo update(UserInfo info);

}
