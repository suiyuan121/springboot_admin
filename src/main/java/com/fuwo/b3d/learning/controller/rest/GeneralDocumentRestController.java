package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
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
@RequestMapping("/api/generalDocuments")
public class GeneralDocumentRestController {

    @Autowired
    private GeneralDocumentService documentService;


    @GetMapping(value = "/search/groupBySubjectOrderByViews")
    public String groupBySubjectOrderByViews(Pageable pageable) {
        List<GeneralDocument.SubjectEnum> types = documentService.getSubjectTypes();
        JSONArray documents = null;
        JSONObject map = new JSONObject();
        for (GeneralDocument.SubjectEnum item : types) {
            if (item != null) {
                documents = JSONArray.fromObject(documentService.findTopCountBySubjectOrderByViews(item, pageable).getContent());
                map.put(item.toString(), documents);
            }
        }

        JSONObject ret = new JSONObject();
        ret.put("content", map);
        return ret.toString();
    }

    @GetMapping(value = "/search/findAll")
    public Page<GeneralDocument> findAll(GeneralDocument document, Pageable pageable) {

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        Example<GeneralDocument> example = Example.of(document, matcher);
        Page<GeneralDocument> page = documentService.findAll(example, pageable);
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
        GeneralDocument generalDocument = documentService.get(id);
        if (generalDocument == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        generalDocument.setViewsIncrease(generalDocument.getViewsIncrease() + amount);
        documentService.save(generalDocument);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }
}