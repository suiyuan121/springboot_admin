package com.fuwo.b3d.user.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.service.BeginnerDocumentService;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.model.UserModel;
import com.fuwo.b3d.user.service.UserModelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userModels")
public class UserModelRestController {

    @Autowired
    private UserModelService userModelService;

    @Autowired
    private ModelService modelService;


    @RequestMapping(value = "/search/findAll")
    public Page<UserModel> findAll(UserModel userModel, Pageable pageable) {

        Example<UserModel> example = Example.of(userModel);
        Page<UserModel> page = userModelService.findAll(example, pageable);
        return page;
    }


    @PostMapping(value = "/deleteModel")
    public RestResult deleteModel(Integer uid, String modelNo) {
        RestResult restResult = new RestResult();

        if (uid == null || StringUtils.isBlank(modelNo)) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Model model = modelService.getByModelNo(modelNo);
        if (model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        List<UserModel> userModels = userModelService.findByUidAndModelId(uid, model.getId());
        if (userModels.size() <= 0) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        for (UserModel item : userModels) {
            item.setStatus(StatusEnum.DISABLE);
            userModelService.save(item);
        }

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());

        return restResult;
    }

}