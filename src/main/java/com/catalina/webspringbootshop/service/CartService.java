package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.CartItem;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    List<CartItem> listCartItems(User user);
    Integer addProduct(Integer product_id, Integer quantity, User user);
    void updateQuantity(Integer productId, Integer quantity, User user);
    void removeProduct(User user);
    void removeOneProductById(Integer productId, User user);

}
