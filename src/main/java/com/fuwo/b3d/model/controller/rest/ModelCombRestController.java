package com.fuwo.b3d.model.controller.rest;

import com.fuwo.b3d.enums.PriceTyleEnum;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.service.ModelCombService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modelCombs")
public class ModelCombRestController {

    @Autowired
    private ModelCombService modelCombService;


    @GetMapping(value = "/{id}")
    public String get(@PathVariable Long id) {
        JSONObject ret = new JSONObject();
        if (id != null) {
            ModelComb modelComb = modelCombService.get(id);
            if (modelComb != null) {
                ret.put("content", modelComb);
                return ret.toString();
            }
        }
        return ret.toString();
    }


    @GetMapping(value = "/search/findAll")
    public Page<ModelComb> findAll(ModelComb modelComb, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        Example<ModelComb> example = Example.of(modelComb, matcher);
        Page<ModelComb> page = modelCombService.findAll(example, pageable);
        return page;
    }

    @GetMapping(value = "/search/findByCond")
    public Page<ModelComb> findAll(ModelComb modelComb, Pageable pageable, PriceTyleEnum priceType) {

        return modelCombService.findByCond(modelComb, priceType, pageable);
    }


}