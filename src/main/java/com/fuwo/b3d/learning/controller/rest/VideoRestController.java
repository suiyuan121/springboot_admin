package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/api/videos")
public class VideoRestController {


    @Autowired
    private VideoService videoService;


    @PostMapping(value = "/{id}/addPlays")
    public RestResult addViews(@PathVariable("id") Long id, Integer amount) {
        RestResult restResult = new RestResult();
        if (id == null || amount == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Video video = videoService.get(id);
        if (video == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }
        video.setPlaysIncrease(video.getPlaysIncrease() + amount);
        videoService.save(video);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }

    @GetMapping(value = "/search/findAll")
    public Page<Video> findAll(Video video, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        Example<Video> example = Example.of(video, matcher);
        Page<Video> page = videoService.findAll(example, pageable);
        return page;
    }
}

