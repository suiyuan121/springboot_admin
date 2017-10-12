package com.fuwo.b3d.event;

import com.fuwo.b3d.checkin.service.CheckinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventsListener {

    private final Logger logger = LoggerFactory.getLogger(EventsListener.class);


    @Autowired
    private CheckinService checkinService;

    @Async
    @EventListener
    public void eventHandler(CheckinEvent event) {
        Integer uid = null;
        try {
            uid = event.getSource();
            if (uid != null) {
                checkinService.checkin(uid);
            }
        } catch (Exception e) {
            logger.error(String.format("签到异步任务失败：uid=%s,errmsg=%s", uid, e.getMessage()));
        }

    }
}
