package com.catalina.webspringbootshop.dto;

import com.catalina.webspringbootshop.entity.Category;

public class ProductForm {

    private String name,  description;
    private Category category;
    private int price, unit_in_stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUnit_in_stock() {
        return unit_in_stock;
    }

    public void setUnit_in_stock(int unit_in_stock) {
        this.unit_in_stock = unit_in_stock;
    }
}
