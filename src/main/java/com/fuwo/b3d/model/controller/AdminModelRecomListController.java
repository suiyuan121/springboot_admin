package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.model.ModelRecom;
import com.fuwo.b3d.model.service.ModelRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/modelRecom")
public class AdminModelRecomListController {

    @Autowired
    private ModelRecomService modelRecomService;


    @GetMapping("/list")
    public String list(Model model, Pageable pageable, ModelRecom recmModel) {

        Sort sort = new Sort(Sort.Direction.DESC, "modified");

        Example<ModelRecom> example = Example.of(recmModel);

        Page<ModelRecom> recmModels = modelRecomService.findAll(example, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort));
        model.addAttribute("page", recmModels);

        model.addAttribute("types", ModelPack.TypeEnum.values());
        model.addAttribute("states", StateEnum.values());

        return "model/model_recom_list";
    }

}
