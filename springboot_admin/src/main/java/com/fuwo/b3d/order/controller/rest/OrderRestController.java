package com.fuwo.b3d.order.controller.rest;

import com.fuwo.b3d.common.RestResult;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.order.model.Order;
import com.fuwo.b3d.order.service.OrderService;
import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    private static String LOCK_KEY = "redis_lock_order_%s_%s";


    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "/pay")
    public RestResult pay(String no) {
        RestResult restResult = new RestResult();
        if (StringUtils.isBlank(no)) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Order order = orderService.findByNo(no);
        if (order == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        //商品类型
        String type = StringUtils.substring(no, 24, 26);
        //商品主键值
        String pk = StringUtils.substring(no, 26, 36);

        String key = String.format(LOCK_KEY, order.getUserInfo().getId(), type + pk);
        String value = UUID.randomUUID().toString();
        boolean lock = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
        if (!lock) {
            restResult.setCode(RestResult.ResultCodeEnum.DUPLICATE_REQUEST.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.DUPLICATE_REQUEST.getDesc());
            return restResult;
        }
        try {
            //请求支付
            boolean ret = orderService.pay(order);
            if (ret) {
                restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
                restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
            }
        } catch (Exception e) {
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(e.getMessage());
        } finally {
            //删除key 防止死锁
            if (StringUtils.equals(redisTemplate.opsForValue().get(key).toString(), value)) {
                redisTemplate.delete(key);
            }
        }

        return restResult;
    }


    @PostMapping(value = "/cancel")
    public RestResult pay(Order orderDto) {
        RestResult restResult = new RestResult();
        if (StringUtils.isBlank(orderDto.getNo())) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }
        Order order = orderService.findByNo(orderDto.getNo());
        if (order == null) {
            restResult.setCode(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.NOT_EXIST_ERR.getDesc());
            return restResult;
        }

        order.setCancelledReason(orderDto.getCancelledReason());
        order.setState(Order.OrderStatusEnum.FAIL);
        orderService.save(order);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }

    @PostMapping(value = "/create")
    public RestResult create(Order order, Integer uid) {
        RestResult restResult = new RestResult();

        if (StringUtils.isNotBlank(order.getNo()) || order.getUserInfo() == null) {
            restResult.setCode(RestResult.ResultCodeEnum.PARA_ERR.getCode());
            restResult.setMsg(RestResult.ResultCodeEnum.PARA_ERR.getDesc());
            return restResult;
        }

        UserInfo userInfo = userService.get(uid);
        order.setUserInfo(userInfo);

        order.setStatus(StatusEnum.ENABLE);
        order.setState(Order.OrderStatusEnum.FAIL);
        order.setCreated(new Date());
        order.setModified(new Date());
        orderService.save(order);

        restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
        restResult.setMsg(RestResult.ResultCodeEnum.SUCC.getDesc());
        return restResult;
    }


    @GetMapping(value = "/search/findAll")
    public Page<Order> findAll(Order order, Pageable pageable) {

        Example<Order> example = Example.of(order);
        Page<Order> page = orderService.findAll(example, pageable);
        return page;
    }
}
