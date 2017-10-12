package com.fuwo.b3d.order.service;

import com.fuwo.b3d.order.model.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {

    Page<Order> pageQuery(Order order, Pageable pageable, String searchText, Date sDate, Date edate);

    Order save(Order order);

    Order findByNo(String no);

    boolean pay(Order order);

    Page<Order> findAll(Example<Order> example, Pageable pageable);

    List<Order> findByCreatedBetween(Date sDate, Date endDate);


}
