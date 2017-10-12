package com.fuwo.b3d.order.service.repository;

import com.fuwo.b3d.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(excerptProjection = Order.class)
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByNo(String no);

    List<Order> findByCreatedBetween(Date start, Date end);
}
