package com.catalina.webspringbootshop.repository;

import com.catalina.webspringbootshop.entity.Category;
import com.catalina.webspringbootshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);

    Product findByName(String name);

    List<Product> findAllByOrderByIdAsc();

    List<Product> findAllByCategory(Category category);
}
