package com.fuwo.b3d.sampleroom.controller;

import com.fuwo.b3d.sampleroom.model.SampleRoom;
import com.fuwo.b3d.sampleroom.service.SampleRoomService;
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
@RequestMapping(BASE_MAPPING + "/sampleroom")
public class AdminSampleRoomListController {

    @Autowired
    SampleRoomService sampleRoomService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable, SampleRoom sampleRoom) {

        Example<SampleRoom> example = Example.of(sampleRoom);
        Page<SampleRoom> sampleRooms = sampleRoomService.findAll(example, pageable);

        model.addAttribute("page", sampleRooms);
        return "sampleroom/sampleroom_list";
    }
}
