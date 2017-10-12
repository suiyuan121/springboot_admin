package com.fuwo.b3d.designcase.controller;

import com.fuwo.b3d.designcase.model.Case;
import com.fuwo.b3d.designcase.service.CaseService;
import com.fuwo.b3d.enums.StateEnum;
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
@RequestMapping(BASE_MAPPING + "/case")
public class AdminCaseListController {
    @Autowired
    private CaseService caseService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable, String searchText, Case designCase) {

        Page<Case> designCases = caseService.pageQuery(designCase, pageable,searchText);

        model.addAttribute("page", designCases);
        model.addAttribute("openEnum", Case.openEnum.values());
        model.addAttribute("searchText",searchText);
        return "case/case_list";
    }



}
