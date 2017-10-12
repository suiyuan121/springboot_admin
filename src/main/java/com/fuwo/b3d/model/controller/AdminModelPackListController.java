package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelPackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/modelpack")
public class AdminModelPackListController {

    @Autowired
    private ModelPackService modelPackService;


    @GetMapping("/list")
    public String list(Model model, Pageable pageable, ModelPack modelPack, String searchText) {

        modelPack.setName(searchText);
        Page<ModelPack> modelPacks = modelPackService.pageQuery(modelPack, searchText, pageable);

        model.addAttribute("page", modelPacks);

        model.addAttribute("types", ModelPack.TypeEnum.values());
        model.addAttribute("states", StateEnum.values());

        return "model/modelpack_list";
    }

}
