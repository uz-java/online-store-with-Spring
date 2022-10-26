package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.Order;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import com.catalina.webspringbootshop.repository.OrderRepository;
import com.catalina.webspringbootshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService{
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public UserServiceImplementation userServiceImplementation;

    @Override
    public String saveOrder(Order order, RedirectAttributes attr, HttpServletRequest req) {
        if (userServiceImplementation.findById(order.getUser().getId()) != null) {
            orderRepository.save(order);
            return "redirect:/";
        }
        else {
            attr.addFlashAttribute("error", "user_id not found in DB!");
            return "redirect:/saveOrder";
        }
    }

    @Override
    public Order findById(int id) {return orderRepository.findById(id); }

    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

}
