package com.catalina.webspringbootshop.repository;

import com.catalina.webspringbootshop.entity.CartItem;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public List<CartItem> findByUser(User user);
    public CartItem findByUserAndProduct(User user, Product product);

    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.product.id = ?2 "
    + "AND c.user.id = ?3 ")
    @Modifying
    public void updateQuantity(Integer quantity, Integer productId, Integer userid);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.product.id = ?2")
    public void deleteByUserAndProduct(Integer userId, Integer productId);

    void deleteByUser(User user);
}
