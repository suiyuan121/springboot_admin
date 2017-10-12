package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.BeginnerDocumentService;
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
@RequestMapping(BASE_MAPPING + "/learning/beginnerDocument")
public class AdminBeginnerDocumentRestController {

    @Autowired
    private BeginnerDocumentService beginnerDocumentService;


    @PostMapping(value = "/delete")
    public RestResult delete(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();
        if (id == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        BeginnerDocument beginnerDocument = beginnerDocumentService.get(id);
        if (beginnerDocument == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        beginnerDocument.setStatus(StatusEnum.DISABLE);
        beginnerDocument.setModified(new Date());
        beginnerDocument.setModifier(principal.getName());
        beginnerDocumentService.save(beginnerDocument);


        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


}

