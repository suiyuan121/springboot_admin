package com.fuwo.b3d.order.controller;

import com.fuwo.b3d.common.exportFile.view.ExcelView;
import com.fuwo.b3d.order.model.Order;
import com.fuwo.b3d.order.service.OrderService;
import com.fuwo.b3d.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;


@Controller
@RequestMapping(BASE_MAPPING + "/order")
public class AdminOrderListController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/list")
    public String list(Model model, Pageable pageable, Order order, String searchText, String startDate, String endDate) {

        Date sDate = null;
        Date eDate = null;
        if (StringUtils.isNotBlank(startDate)) {
            sDate = DateUtils.getDate(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            eDate = DateUtils.getDate(endDate);
        }

        Page<Order> orders = orderService.pageQuery(order, pageable, searchText, sDate, eDate);

        model.addAttribute("page", orders);

        return "order/order_list";
    }

    @GetMapping(value = "/download")
    public ModelAndView list(Model model, String period, String startDate, String endDate) {
        Date sDate = null;
        Date eDate = null;
        if (StringUtils.isNotBlank(startDate)) {
            sDate = DateUtils.getDate(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            eDate = DateUtils.getDate(endDate);
        }

        if ("week".equals(period)) {
            sDate = org.apache.commons.lang.time.DateUtils.addWeeks(new Date(), -1);
            eDate = new Date();
        }
        List<Order> orders = orderService.findByCreatedBetween(sDate, eDate);


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orders", orders);
        map.put("type", "order");
        ExcelView excelView = new ExcelView();
        return new ModelAndView(excelView, map);
    }

}
