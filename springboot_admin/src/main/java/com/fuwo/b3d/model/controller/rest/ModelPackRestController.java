package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelPackService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modelPacks")
public class ModelPackRestController {

    @Autowired
    private ModelPackService modelPackService;


    @GetMapping(value = "/{id}")
    public String get(@PathVariable Long id) {
        JSONObject ret = new JSONObject();
        if (id != null) {
            ModelPack modelPack = modelPackService.get(id);
            if (modelPack != null) {
                ret.put("content", modelPack);
            }
        }
        return ret.toString();
    }


    @GetMapping(value = "/search/findAll")
    public Page<ModelPack> findAll(ModelPack modelPack, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        Example<ModelPack> example = Example.of(modelPack, matcher);
        Page<ModelPack> page = modelPackService.findAll(example, pageable);
        return page;
    }


    @GetMapping(value = "/search/findByCond")
    public Page<ModelPack> findAll(ModelPack modelPack, Pageable pageable, PriceTyleEnum priceType) {
        return modelPackService.findByCond(modelPack, priceType, pageable);
    }

}

