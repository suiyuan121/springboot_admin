package com.fuwo.b3d.model.controller;

import com.fuwo.b3d.enums.StateEnum;

import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;


@Controller
@RequestMapping(BASE_MAPPING + "/model")
public class AdminModelListController {

    @Autowired
    private ModelService modelService;

    // @GetMapping("/list")
    public String list(Model modelUi, Pageable pageable, com.fuwo.b3d.model.model.Model model, String searchText, String startDate, String endDate) {

        if (StringUtils.isNotBlank(searchText)) {
            model.setProductName(searchText);
        }
        Date sDate = null;
        Date eDate = null;
        if (StringUtils.isNotBlank(startDate)) {
            sDate = DateUtils.getDate(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            eDate = DateUtils.getDate(endDate);
        }

        Page<com.fuwo.b3d.model.model.Model> models = modelService.pageQuery(model, pageable, sDate, eDate);

        modelUi.addAttribute("page", models);
        modelUi.addAttribute("states", StateEnum.values());
        return "model/model_list";
    }


}
