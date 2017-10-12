package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelPackService;
import com.fuwo.b3d.model.service.ModelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/modelpack")
public class



AdminModelPackRestController {

    @Autowired
    private ModelPackService modelPackService;

    @Autowired
    private ModelService modelService;

    @PostMapping(value = "/setPriority")
    public RestResult setPriority(Long id, Integer priority, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null || priority == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg("参数为空");
            return restResult;
        }

        ModelPack modelPack = modelPackService.get(id);
        if (modelPack != null) {
            modelPack.setPriority(priority);
        }
        modelPack.setModified(new Date());
        modelPack.setModifier(principal.getName());
        modelPackService.save(modelPack);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    @GetMapping(value = "/model")
    public RestResult getModelByModelNo(String modelNo) {
        RestResult restResult = new RestResult();
        if (StringUtils.isBlank(modelNo)) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        com.fuwo.b3d.model.model.Model model = modelService.getByModelNoAndPerfect(modelNo, Model.PerfectEnum.NORMAL);
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
    public RestResult delete(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        ModelPack modelPack = modelPackService.get(id);
        if (modelPack == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        modelPack.setStatus(StatusEnum.DISABLE);
        modelPack.setModified(new Date());
        modelPack.setModifier(principal.getName());
        modelPackService.save(modelPack);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


    @PostMapping(value = "/deleteModel")
    public RestResult deleteModel(Long id, String no) {
        RestResult restResult = new RestResult();

        if (id == null || StringUtils.isBlank(no)) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Model model = modelService.getByModelNoAndPerfect(no, Model.PerfectEnum.NORMAL);
        ModelPack modelPack = modelPackService.get(id);
        if (modelPack == null || model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        boolean result = modelPackService.deleteModel(modelPack, model);
        if (result) {
            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
        }
        return restResult;
    }

}

