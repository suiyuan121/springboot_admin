package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.user.service.UserModelService;
import com.fuwo.b3d.user.service.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/models")
public class ModelRestController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private UserModelService userModelService;


    @GetMapping(value = "/search/findAll")
    public Page<Model> findAll(Model model, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("productName", match -> match.contains());

        Example<Model> example = Example.of(model, matcher);

        Page<Model> page = modelService.findAll(example, pageable);
        return page;
    }

    @GetMapping(value = "/{id}/getBuys")
    public RestResult getBuys(@PathVariable("id") Integer id) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        restResult.setData(userModelService.getBuys(id));
        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


}