package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.VideoService;
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
@RequestMapping(BASE_MAPPING + "/learning/video")
public class AdminVideoListController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable, String searchText, Video video) {

        if (StringUtils.isNotBlank(searchText)) {
            video.setTitle(searchText);
            video.setSpeaker(searchText);
        }

        Page<Video> videos = videoService.pageQuery(video, pageable);

        model.addAttribute("categories", Video.CategoryEnum.values());
        model.addAttribute("tags", TagEnum.values());
        model.addAttribute("states", StateEnum.values());
        model.addAttribute("page", videos);

        return "learning/video_list";
    }
}
