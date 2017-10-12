package com.fuwo.b3d.sampleroom.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.sampleroom.model.SampleRoom;
import com.fuwo.b3d.sampleroom.service.SampleRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/admin/sampleroom")
public class AdminSampleRoomRestController {
    @Autowired
    private SampleRoomService sampleRoomService;

    @RequestMapping(value = "/delete")
    public RestResult delete(Long id) {

        return null;
    }
}
