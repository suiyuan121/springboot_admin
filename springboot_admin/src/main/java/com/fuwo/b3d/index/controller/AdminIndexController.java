package com.fuwo.b3d.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "")
public class AdminIndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
