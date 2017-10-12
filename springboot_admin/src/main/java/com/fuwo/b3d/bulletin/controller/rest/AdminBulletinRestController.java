package com.fuwo.b3d.bulletin.controller.rest;

import com.fuwo.b3d.bulletin.model.Bulletin;
import com.fuwo.b3d.bulletin.service.BulletinService;
import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/bulletin")
public class AdminBulletinRestController {

    @Autowired
    private BulletinService bulletinService;

    @PostMapping(value = "/delete")
    public RestResult delete(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Bulletin bulletin = bulletinService.get(id);
        if (bulletin != null) {
            bulletin.setStatus(StatusEnum.DISABLE);
            bulletin.setModifier(principal.getName());
            bulletin.setModified(new Date());
            bulletinService.save(bulletin);
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }

    @PostMapping(value = "/setState")
    public RestResult setStatus(Long id, StateEnum state, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Bulletin bulletin = bulletinService.get(id);
        if (bulletin != null) {
            bulletin.setState(state);
            bulletin.setModifier(principal.getName());
            bulletin.setModified(new Date());
            bulletinService.save(bulletin);
        } else {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());

        return restResult;
    }
}
