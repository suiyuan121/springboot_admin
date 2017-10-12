package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.BeginnerDocumentService;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/beginnerDocuments")
public class BeginnerDocumentRestController {

    @Autowired
    private BeginnerDocumentService documentService;


    @GetMapping(value = "/search/findAll")
    public Page<BeginnerDocument> findAll(BeginnerDocument document, Pageable pageable) {

        Page<BeginnerDocument> page = documentService.findAll(document, pageable);
        return page;
    }


    @PostMapping(value = "/{id}/addViews")
    public RestResult addViews(@PathVariable("id") Long id, Integer amount) {
        RestResult restResult = new RestResult();
        if (id == null || amount == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        BeginnerDocument beginnerDocument = documentService.get(id);
        if (beginnerDocument == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        beginnerDocument.setViewsIncrease(beginnerDocument.getViewsIncrease() + amount);
        documentService.save(beginnerDocument);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }

}