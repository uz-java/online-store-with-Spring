package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.Order;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface OrderService {
    String saveOrder(Order order, RedirectAttributes attr, HttpServletRequest req);
    Order findById(int id);
    List<Order> findAllByUser(User user);
}
