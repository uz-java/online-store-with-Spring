package com.catalina.webspringbootshop.controller;

import com.catalina.webspringbootshop.entity.CartItem;
import com.catalina.webspringbootshop.entity.Category;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import com.catalina.webspringbootshop.repository.CategoryRepository;
import com.catalina.webspringbootshop.repository.ProductRepository;
import com.catalina.webspringbootshop.repository.UserRepository;
import com.catalina.webspringbootshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    private final double TAX = 0.2533;

    @Autowired
    private ProductRepository productRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(value = {"/categories"})
    public String index(ModelMap model) {
        model.addAttribute("model", listAllCategories());
        return "categories";
    }

    @GetMapping(value = {"/category/{id}"})
    public String get(@PathVariable("id") int id, ModelMap model, @AuthenticationPrincipal UserDetails userDetails) {
        Category category = categoryRepository.findById(id);

        User user = userRepository.findByUsername(userDetails.getUsername());

        List<CartItem> cartItems = cartService.listCartItems(user);
        double cartSum = cartItems.stream().mapToDouble(o -> o.getProduct().getPrice()).sum();
        double totalCartSum = Math.floor((cartSum + cartSum * TAX) * 100) / 100;
        int totalCartItems = cartItems.stream().mapToInt(el -> el.getQuantity()).sum();
        List<Integer> productIds = cartItems.stream().map(i -> i.getProduct().getId()).collect(Collectors.toList());


        model.addAttribute("products", listProductsByCategory(category));
        model.addAttribute("name", category.getName());
        model.addAttribute("productIds", productIds);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("totalCartSum", totalCartSum);
        model.addAttribute("totalCartItems", totalCartItems);

        return "category";
    }
    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> listProductsByCategory(Category category) { return productRepository.findAllByCategory(category);}
}
