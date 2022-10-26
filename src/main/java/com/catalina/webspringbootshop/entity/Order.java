package com.catalina.webspringbootshop.entity;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Column(name = "order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int quantity;
    private float total;
    private Date orderDate;
    @ManyToMany
    @JoinTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id", nullable=false), inverseJoinColumns =
    @JoinColumn(name = "product_id", nullable=false))
    List<Product> listProducts = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    public Order() {}


    public Order(@NotNull User user, int quantity, float total, Date orderDate, List<Product> listProducts) {
        this.user = user;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = orderDate;
        this.listProducts = listProducts;
    }
}


