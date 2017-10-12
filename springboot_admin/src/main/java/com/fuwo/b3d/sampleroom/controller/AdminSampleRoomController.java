package com.fuwo.b3d.sampleroom.controller;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.ModelComb;
import com.fuwo.b3d.sampleroom.model.SampleRoom;
import com.fuwo.b3d.sampleroom.service.SampleRoomService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/sampleroom")
public class AdminSampleRoomController {

    //样板间最大数量
    private static final Integer SAMPLEROOM_MAX_AMOUNT = 5;

    @Autowired
    private SampleRoomService sampleRoomService;

    @GetMapping("/save")
    public String add(Model model, SampleRoom sampleRoom) {
        if (sampleRoom.getId() != null) {
            //编辑
            sampleRoom = sampleRoomService.get(sampleRoom.getId());
        }
        model.addAttribute("sampleRoom", sampleRoom);
        return "sampleroom/sampleroom";
    }

    @PostMapping("/save")
    public String add(SampleRoom sampleRoomDto, Model model, @NotNull Principal principal) {

        //校验是否超过5个样板间
        Integer amount = sampleRoomService.countAll();
        if (amount != null && amount > SAMPLEROOM_MAX_AMOUNT) {
            model.addAttribute("errorMessages", "已超过五个样板间，请删除后再操作");
            model.addAttribute("sampleRoom", sampleRoomDto);
            return "sampleroom/sampleroom";
        }

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(sampleRoomDto.getBrand())) {
            errorMessages.append("品牌不能为空！");
        }
        if (StringUtils.isBlank(sampleRoomDto.getPrevUrl())) {
            errorMessages.append("样板间缩率图不能为空！");
        }
        if (StringUtils.isBlank(sampleRoomDto.getTitle())) {
            errorMessages.append("标题不能为空！");
        }
        if (StringUtils.isBlank(sampleRoomDto.getPanoUrl())) {
            errorMessages.append("全景图链接不能为空！");
        }
        if (StringUtils.isBlank(sampleRoomDto.getDescription())) {
            errorMessages.append("描述不能为空！");
        }
        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("sampleRoom", sampleRoomDto);
            return "sampleroom/sampleroom";
        }

        SampleRoom sampleRoom;
        if (sampleRoomDto.getId() == null) {
            //创建
            sampleRoomDto.setCreator(principal.getName());
            sampleRoomDto.setCreated(new Date());
            sampleRoom = sampleRoomDto;
        } else {
            sampleRoom = sampleRoomService.get(sampleRoomDto.getId());
            BeanUtils.copyProperties(sampleRoomDto, sampleRoom, "id", "created", "creator", "priority");

        }
        sampleRoom.setModified(new Date());
        sampleRoom.setModifier(principal.getName());
        sampleRoom.setStatus(StatusEnum.ENABLE);
        sampleRoomService.save(sampleRoom);

        return "redirect:./list";
    }
}