package com.fuwo.b3d.user.controller.rest;

import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.user.model.UserModel;
import com.fuwo.b3d.user.model.UserModelPack;
import com.fuwo.b3d.user.service.UserModelPackService;
import com.fuwo.b3d.user.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userModelPacks")
public class UserModelPackRestController {

    @Autowired
    private UserModelPackService userModelPackService;


    @RequestMapping(value = "/search/findAll")
    public Page<UserModelPack> findAll(UserModelPack userModelPack, Pageable pageable) {

        Example<UserModelPack> example = Example.of(userModelPack);
        Page<UserModelPack> page = userModelPackService.findAll(example, pageable);
        return page;
    }


}