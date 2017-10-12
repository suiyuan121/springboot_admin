package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.learning.model.GeneralDocument;
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
@RequestMapping(BASE_MAPPING + "/learning/generalDocument")
public class AdminGeneralDocumentListController {

    @Autowired
    private GeneralDocumentService documentService;


    @GetMapping("/list")
    public String list(Model model, Pageable pageable, String searchText, GeneralDocument document) {

        if (StringUtils.isNotBlank(searchText)) {
            document.setTitle(searchText);
        }
        Page<GeneralDocument> documents = documentService.pageQuery(document, pageable);
        model.addAttribute("page", documents);

        model.addAttribute("subjects", GeneralDocument.SubjectEnum.values());
        model.addAttribute("states", StateEnum.values());
        model.addAttribute("tags", TagEnum.values());

        return "learning/generalDocument_list";
    }
}
