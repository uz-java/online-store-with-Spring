package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void edit(int id, Product newProduct) {
        Product oldProduct = productRepository.getOne(id);
        oldProduct.setName(newProduct.getName());
        oldProduct.setUnit_in_stock(newProduct.getUnit_in_stock());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        save(oldProduct);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(findById(id));
    }

    @Override
    public List<Product> findAllByOrderByAsc() {
        return productRepository.findAllByOrderByIdAsc();
    }
}
