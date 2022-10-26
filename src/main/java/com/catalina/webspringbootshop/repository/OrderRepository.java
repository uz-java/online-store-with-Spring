package com.catalina.webspringbootshop.repository;

import com.catalina.webspringbootshop.entity.Order;
import com.catalina.webspringbootshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findById(int id);
    List<Order> findAllByUser(User user);
}
