package com.catalina.webspringbootshop.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartitem_id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    public Integer getId() {
        return cartitem_id;
    }

    public void setId(Integer id) {
        this.cartitem_id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Transient
    public float getSubtotal() {
        return this.product.getPrice() * quantity;
    }
}
