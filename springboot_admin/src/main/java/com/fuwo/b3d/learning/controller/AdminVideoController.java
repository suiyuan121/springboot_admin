package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.learning.model.Video;
import com.fuwo.b3d.learning.service.VideoService;
import com.fuwo.b3d.model.model.ModelPack;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/learning/video")
public class AdminVideoController {


    @Autowired
    private VideoService videoService;


    @GetMapping("/save")
    public String add(Model model, Video video) {

        if (video.getId() != null) {
            //来源于编辑
            video = videoService.get(video.getId());
        }
        model.addAttribute("video", video);
        model.addAttribute("tags", TagEnum.values());
        model.addAttribute("categories", Video.CategoryEnum.values());
        model.addAttribute("states", StateEnum.values());

        return "learning/video";
    }

    @PostMapping(value = "/save")
    public String addPost(Video videoDto, Model model,@NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(videoDto.getCover())) {
            errorMessages.append("封面图不能为空！");
        }
        if (StringUtils.isBlank(videoDto.getUrl())) {
            errorMessages.append("视频链接不能为空！");
        }
        if (StringUtils.isBlank(videoDto.getTitle())) {
            errorMessages.append("视频标题不能为空！");
        }
        if (StringUtils.isBlank(videoDto.getSpeaker())) {
            errorMessages.append("主讲人不能为空！");
        }
        if (videoDto.getCategory() == null) {
            errorMessages.append("分类不能为空！");
        }
        if (videoDto.getState() == null) {
            errorMessages.append("状态不能为空！");
        }
        if (videoDto.getPlaysInitial() == null) {
            errorMessages.append("播放不能为空！");
        }

        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("video", videoDto);
            model.addAttribute("tags", TagEnum.values());
            model.addAttribute("categories", Video.CategoryEnum.values());
            model.addAttribute("states", StateEnum.values());
            return "learning/video";
        }

        Video video;
        if (videoDto.getId() == null) {
            //创建
            videoDto.setCreated(new Date());
            videoDto.setCreator(principal.getName());
            videoDto.setPlaysIncrease(videoDto.getPlaysInitial());
            video = videoDto;
        } else {
            video = videoService.get(videoDto.getId());
            BeanUtils.copyProperties(videoDto, video, "id", "playsIncrease", "created", "creator", "priority", "tag");
            video.setPlaysIncrease(video.getPlaysIncrease() - video.getPlaysInitial() + videoDto.getPlaysInitial());
        }
        video.setModified(new Date());
        video.setModifier(principal.getName());
        video.setStatus(StatusEnum.ENABLE);

        videoService.save(video);

        return "redirect:./list";
    }


}
