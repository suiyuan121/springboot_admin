package com.fuwo.b3d.checkin.controller.rest;

import com.fuwo.b3d.checkin.model.Checkin;
import com.fuwo.b3d.checkin.service.CheckinService;
import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.event.CheckinEvent;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.UserService;
import com.fuwo.b3d.utils.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkins")
public class CheckinRestController {

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping(value = "/checkin")
    public RestResult checkin(Integer uid) {

        RestResult restResult = new RestResult();
        if (uid == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        UserInfo userInfo = userService.get(uid);
        if (userInfo == null) {
            restResult.setCode(RestResult.ResultCodeEnum.USER_NOT_EXISTS.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.USER_NOT_EXISTS.getDesc());
            return restResult;
        }
        try {
            //检查今天是否签到过
            Checkin today = checkinService.findCurrentByUid(uid);
            if (today != null) {
                restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
                restResult.setMsg("今日已签到过，不可重复签到");
                return restResult;
            } else {
                //签到--异步任务执行
                CheckinEvent checkinEvent = new CheckinEvent(uid);
                publisher.publishEvent(checkinEvent);
            }

            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        } catch (Exception e) {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(e.getMessage());
        }

        return restResult;
    }

    @GetMapping(value = "/search/findCurrentWeek")
    public RestResult findCurrentWeek(Integer uid) {
        RestResult restResult = new RestResult();

        JSONObject result = new JSONObject();

        Date sDate = DateUtils.getMondayOfWooek();
        Date eDate = DateUtils.getSundayOfWeek();

        List<Checkin> list = checkinService.findByDate(uid, sDate, eDate);

        try {
            result.put("sysdate", new Date());
            result.put("checkins", JSONArray.fromObject(list).toString());
            restResult.setData(result);
        } catch (JSONException e) {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.ERROR.getDesc());
        }

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());

        return restResult;
    }


}