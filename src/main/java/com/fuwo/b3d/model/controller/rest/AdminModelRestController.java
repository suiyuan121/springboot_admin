package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelPackService;
import com.fuwo.b3d.model.service.ModelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/model")
public class AdminModelRestController {

    @Autowired
    private ModelService modelService;


    @PostMapping(value = "/setPrice")
    public RestResult setPriority(String no, Integer price) {
        RestResult restResult = new RestResult();

        if (StringUtils.isBlank(no) || price == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg("参数为空");
            return restResult;
        }

        Model model = modelService.getByModelNoAndPerfect(no, Model.PerfectEnum.NORMAL);
        if (model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        model.setDiscountPrice(price);
        model.setModified(new Date());
        modelService.save(model);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    @PostMapping(value = "/setCategory")
    public RestResult setCatgory(String no, Model.CategoryEnum category) {
        RestResult restResult = new RestResult();

        if (StringUtils.isBlank(no) || category == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg("参数为空");
            return restResult;
        }

        Model model = modelService.getByModelNoAndPerfect(no, Model.PerfectEnum.NORMAL);
        if (model == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        model.setCategory(category);
        model.setModified(new Date());
        modelService.save(model);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    //@PostMapping(value = "/delete")
    public RestResult delete(Integer id) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Model model = modelService.get(id);
        if (model == null) {
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.ERROR.getDesc());
            return restResult;
        }
        model.setState(9);//删除
        model.setModified(new Date());
        modelService.save(model);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


    @PostMapping(value = "/setPriceForBatch")
    public RestResult setPriceForBatch(String nos, Integer price) {
        RestResult restResult = new RestResult();
        if (StringUtils.isBlank(nos) || price == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        String [] array=nos.split(",");
        modelService.updatePrice(Arrays.asList(array), price);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }

}

