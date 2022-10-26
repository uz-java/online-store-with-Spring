package com.catalina.webspringbootshop.dto;


import com.catalina.webspringbootshop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class OrderRequest {

    private Order order;

    private OrderRequest() {}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
