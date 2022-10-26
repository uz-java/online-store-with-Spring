package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.Product;

import java.util.List;

public interface ProductService {
    void save(Product product);

    void edit(int id, Product newProduct);

    void delete(int id);

    Product findById(int id);

    List<Product> findAllByOrderByAsc();
}
