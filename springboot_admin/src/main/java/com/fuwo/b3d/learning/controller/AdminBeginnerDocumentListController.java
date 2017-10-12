package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.BeginnerDocumentService;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
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
@RequestMapping(BASE_MAPPING + "/learning/beginnerDocument")
public class AdminBeginnerDocumentListController {

    @Autowired
    private BeginnerDocumentService beginnerDocumentService;


    @GetMapping("/list")
    public String list(Model model, Pageable pageable, BeginnerDocument document) {
        Page<BeginnerDocument> documents = beginnerDocumentService.findAll(document, pageable);
        model.addAttribute("page", documents);

        return "learning/beginnerDocument_list";
    }
}
