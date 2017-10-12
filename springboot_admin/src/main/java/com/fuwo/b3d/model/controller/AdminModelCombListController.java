package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.model.service.ModelCombService;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;


@Controller
@RequestMapping(BASE_MAPPING + "/modelcomb")
public class AdminModelCombListController {

    @Autowired
    private ModelCombService modelCombService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable, ModelComb modelComb, String searchText) {

        if (StringUtils.isNotBlank(searchText)) {
            modelComb.setDesignNo(searchText);
            modelComb.setName(searchText);
        }

        Page<ModelComb> modelCombs = modelCombService.pageQuery(modelComb, pageable);

        model.addAttribute("page", modelCombs);
        model.addAttribute("spaces", ModelComb.SpaceEnum.values());
        model.addAttribute("styles", ModelComb.StyleEnum.values());
        return "model/modelcomb_list";
    }


}
