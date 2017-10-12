package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.model.model.ModelRecom;
import com.fuwo.b3d.model.service.ModelRecomService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modelRecoms")
public class ModelRecomRestController {

    @Autowired
    private ModelRecomService recmModelService;


    @GetMapping(value = "/{id}")
    public String get(@PathVariable Integer id) {
        JSONObject ret = new JSONObject();
        if (id != null) {
            ModelRecom recmModel = recmModelService.get(id);
            if (recmModel != null) {
                ret.put("content", recmModel);
            }
        }
        return ret.toString();
    }


    @GetMapping(value = "/search/findAll")
    public Page<ModelRecom> findAll(ModelRecom recmModel, Pageable pageable) {

        Example<ModelRecom> example = Example.of(recmModel);
        Page<ModelRecom> page = recmModelService.findAll(example, pageable);
        return page;
    }


}

