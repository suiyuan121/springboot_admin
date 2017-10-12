
package com.fuwo.b3d.bulletin.controller;

import com.fuwo.b3d.bulletin.model.Bulletin;

import com.fuwo.b3d.bulletin.service.BulletinService;
import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;


@Controller
@RequestMapping(BASE_MAPPING + "/bulletin")
public class AdminBulletinListController {

    @Autowired
    private BulletinService bulletinService;

    @GetMapping(value = "/list")
    public String list(Model model, Pageable pageable, Bulletin bulletin) {

        Example<Bulletin> example = Example.of(bulletin);
        Page<Bulletin> bulletins = bulletinService.pageQuery(example, pageable);

        model.addAttribute("page", bulletins);
        model.addAttribute("states", StateEnum.values());
        return "bulletin/bulletin_list";
    }
}

