package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.service.ModelCombService;
import com.fuwo.b3d.model.service.ModelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/modelcomb")
public class AdminModelCombRestController {

    @Autowired
    private ModelCombService modelCombService;

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

        ModelComb modelComb = modelCombService.get(id);
        if (modelComb != null) {
            modelComb.setPriority(priority);
        }
        modelComb.setModified(new Date());
        modelComb.setModifier(principal.getName());
        modelCombService.save(modelComb);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
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
        ModelComb modelComb = modelCombService.get(id);
        if (modelComb != null) {
            modelComb.setStatus(StatusEnum.DISABLE);
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.ERROR.getDesc());
        }
        modelComb.setModified(new Date());
        modelComb.setModifier(principal.getName());
        modelCombService.save(modelComb);

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
        ModelComb modelComb = modelCombService.get(id);
        if (modelComb == null || model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        boolean result = modelCombService.deleteModel(modelComb, model);
        if (!result) {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        }
        return restResult;
    }

    @PostMapping(value = "/deleteModelForBatch")
    public RestResult deleteModelForBatch(Long id, String nos) {

        RestResult restResult = new RestResult();

        if (StringUtils.isBlank(nos) || id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        ModelComb modelComb = modelCombService.get(id);
        if (modelComb == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        String[] array = nos.split(",");
        List<Model> models = modelService.findByNos(Arrays.asList(array));
        modelCombService.deleteModels(modelComb, models);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());


        return restResult;
    }
}