package com.fuwo.b3d.learning.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
import com.fuwo.b3d.learning.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@RestController
@RequestMapping(BASE_MAPPING + "/learning/video")
public class AdminVideoRestController {


    @Autowired
    private VideoService videoService;


    @PostMapping(value = "/setPriority")
    public RestResult setPriority(Long id, Integer priority, @NotNull Principal principal) {
        RestResult restResult = new RestResult();

        if (id == null || priority == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        Video video = videoService.get(id);
        if (video != null) {
            video.setPriority(priority);
        }
        video.setModified(new Date());
        video.setModifier(principal.getName());
        videoService.save(video);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        return restResult;
    }

    @PostMapping(value = "/delete")
    public RestResult delete(Long id, @NotNull Principal principal) {
        RestResult restResult = new RestResult();
        if (id == null) {
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
        video.setStatus(StatusEnum.DISABLE);
        video.setModified(new Date());
        video.setModifier(principal.getName());
        videoService.save(video);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }
}

