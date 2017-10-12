package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelRecom;
import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.model.service.ModelRecomService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/modelRecom")
public class AdminModelRecomRestController {

    @Autowired
    private ModelRecomService modelRecomService;

    @Autowired
    private ModelService modelService;

    @GetMapping(value = "/model")
    public RestResult getModelByModelNo(String modelNo) {
        RestResult restResult = new RestResult();
        if (StringUtils.isBlank(modelNo)) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Model model = modelService.getByModelNoAndPerfect(modelNo, Model.PerfectEnum.PERFECT);
        if (model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());

        }
        restResult.setData(model);
        return restResult;
    }

    @PostMapping(value = "/delete")
    public RestResult delete(Integer id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        ModelRecom recmModel = modelRecomService.get(id);
        if (recmModel == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        recmModel.setStatus(StatusEnum.DISABLE);
        recmModel.setModified(new Date());
        recmModel.setModifier(principal.getName());
        modelRecomService.save(recmModel);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }




}

