package com.fuwo.b3d.user.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.model.model.ModelPack;
import com.fuwo.b3d.model.service.ModelService;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.websocket.server.PathParam;
import java.util.Date;


@RestController
@RequestMapping("/api/userInfoes")
public class UserRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private ModelService modelService;

    @GetMapping(value = "/{id}")
    public String get(@PathVariable("id") Integer id) {
        JSONObject ret = new JSONObject();
        if (id != null) {
            UserInfo userInfo = userService.get(id);
            if (userInfo != null) {
                ret.put("content", userInfo);
                return ret.toString();
            }
        }
        return ret.toString();
    }



}
