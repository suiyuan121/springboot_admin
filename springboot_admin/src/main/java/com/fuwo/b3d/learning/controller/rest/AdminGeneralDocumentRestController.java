package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/learning/generalDocument")
public class AdminGeneralDocumentRestController {

    @Autowired
    private GeneralDocumentService generalDocumentService;


    @PostMapping(value = "/setPriority")
    public RestResult setPriority(Long id, Integer priority, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null || priority == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg("参数为空");
            return restResult;
        }

        GeneralDocument generalDocument = generalDocumentService.get(id);
        if (generalDocument == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        generalDocument.setPriority(priority);
        generalDocument.setModified(new Date());
        generalDocument.setModifier(principal.getName());
        generalDocumentService.save(generalDocument);

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
        GeneralDocument generalDocument = generalDocumentService.get(id);
        if (generalDocument == null) {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.ERROR.getDesc());
        } else {
            generalDocument.setStatus(StatusEnum.DISABLE);
            generalDocument.setModified(new Date());
            generalDocument.setModifier(principal.getName());
            generalDocumentService.save(generalDocument);
        }

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


}

