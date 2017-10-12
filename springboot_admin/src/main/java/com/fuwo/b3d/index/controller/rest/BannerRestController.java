package com.fuwo.b3d.index.controller.rest;

import com.fuwo.b3d.index.model.Banner;
import com.fuwo.b3d.index.service.BannerService;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerRestController {

    @Autowired
    private BannerService bannerService;


    @RequestMapping(value = "/search/findAll")
    public Page<Banner> findAll(Banner banner, Pageable pageable) {
        Example<Banner> example = Example.of(banner);
        Page<Banner> page = bannerService.findAll(example, pageable);
        return page;
    }

}