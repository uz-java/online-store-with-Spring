package com.catalina.webspringbootshop.controller;

import com.catalina.webspringbootshop.dto.ProductForm;
import com.catalina.webspringbootshop.entity.*;
import com.catalina.webspringbootshop.repository.CategoryRepository;
import com.catalina.webspringbootshop.repository.ProductRepository;
import com.catalina.webspringbootshop.repository.UserRepository;
import com.catalina.webspringbootshop.service.CartService;
import com.catalina.webspringbootshop.service.ProductService;
import com.catalina.webspringbootshop.service.UserService;
import com.catalina.webspringbootshop.telegram.Bot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryRepository categoryrepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final double TAX = 0.2533;

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, ProductService productService, CategoryRepository categoryRepository, ServiceBot serviceBot) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.serviceBot = serviceBot;
    }

    private final ServiceBot serviceBot;

    @GetMapping(value = {"/"})
    public String dashboard(ModelMap model, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {


        AtomicLong atomicLong = new AtomicLong();
        long l = atomicLong.get();
        String remoteUser = request.getRemoteAddr();
        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        ServerUser user1 = new ServerUser(l, remoteUser, LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd      HH:mm:ss ")), "Main menu");

        user1.setIpAddress(remoteUser);
        serviceBot.sendMessage(user1, request);


        System.out.println(request.getRemoteAddr());


        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername());

        List<CartItem> cartItems = cartService.listCartItems(user);
        double cartSum = cartItems.stream().mapToDouble(o -> o.getProduct().getPrice()).sum();
        double totalCartSum = Math.floor((cartSum + cartSum * TAX) * 100) / 100;
        int totalCartItems = cartItems.stream().mapToInt(el -> el.getQuantity()).sum();
        List<Integer> productIds = cartItems.stream().map(i -> i.getProduct().getId()).collect(Collectors.toList());

        model.addAttribute("products", getAllProducts());
        model.addAttribute("categories", listAllCategories());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("totalCartSum", totalCartSum);
        model.addAttribute("productIds", productIds);
        model.addAttribute("totalCartItems", totalCartItems);
        model.addAttribute("admin1", true);


        return "index";
    }

    @GetMapping(value = {"/products"})
    public String index(ModelMap model, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {

        AtomicLong atomicLong = new AtomicLong();
        long l = atomicLong.get();
        String remoteUser = request.getRemoteAddr();
        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        ServerUser user1 = new ServerUser(l, remoteUser, LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd      HH:mm:ss ")), "products");

        user1.setIpAddress(remoteUser);
        serviceBot.sendMessage(user1, request);


        System.out.println(request.getRemoteAddr());


        User user = userRepository.findByUsername(userDetails.getUsername());
        List<CartItem> cartItems = cartService.listCartItems(user);
        double cartSum = cartItems.stream().mapToDouble(o -> o.getProduct().getPrice()).sum();
        double totalCartSum = Math.floor((cartSum + cartSum * TAX) * 100) / 100;
        int totalCartItems = cartItems.stream().mapToInt(el -> el.getQuantity()).sum();
        List<Integer> productIds = cartItems.stream().map(i -> i.getProduct().getId()).collect(Collectors.toList());

        model.addAttribute("products", getAllProducts());
        model.addAttribute("categories", listAllCategories());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("totalCartSum", totalCartSum);
        model.addAttribute("productIds", productIds);
        model.addAttribute("totalCartItems", totalCartItems);
        model.addAttribute("admin", true);

        return "products";
    }

    @GetMapping(value = {"/{id}"})
    public String get(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("products", productRepository.findById(id));

        return "product";
    }

    @RequestMapping(value = "/admin/product/new", method = {RequestMethod.POST, RequestMethod.GET})
    public String newProduct(ModelMap model, ProductForm product, HttpServletRequest request, RedirectAttributes attr) {

        AtomicLong atomicLong = new AtomicLong();
        long l = atomicLong.get();
        String remoteUser = request.getRemoteAddr();
        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        ServerUser user1 = new ServerUser(l, remoteUser, LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd      HH:mm:ss ")), "Admin create new product");

        user1.setIpAddress(remoteUser);
        serviceBot.sendMessage(user1, request);


        System.out.println(request.getRemoteAddr());


        if (StringUtils.equals(request.getMethod(), RequestMethod.GET.toString())) {
            model.addAttribute("productForm", new Product());
            model.addAttribute("categories", categoryrepo.findAll());

            return "create-product";
        }


        if (StringUtils.equals(request.getMethod(), RequestMethod.POST.toString())) {
            Category newCategory = categoryRepository.findById(product.getCategory().getId());
            Product newProduct = new Product(product.getName(), product.getPrice(), product.getUnit_in_stock(),
                    product.getDescription(), newCategory);

            productService.save(newProduct);
            logger.debug(String.format("Product with id: %s successfully created.", newProduct.getId()));

            return "redirect:/";
        }

        return invalidRequestResponse(attr);
    }

    @RequestMapping(value = "/product/edit/{id}", method = {RequestMethod.POST})
    public String update(@PathVariable("id") int id, Product product, HttpServletRequest req, RedirectAttributes attr) {
        if (StringUtils.equals(req.getMethod(), RequestMethod.POST.toString())) {
            productService.edit(id, product);
            logger.debug(String.format("Product with id: %s has been successfully edited.", id));
            return "redirect:/";
        }

        return invalidRequestResponse(attr);
    }

    @RequestMapping(value = "/product/delete/{id}", method = {RequestMethod.POST})
    public String destroy(@PathVariable("id") int productId) {
        Product product = productService.findById(productId);

        if (product != null) {
            productService.delete(productId);
            logger.debug(String.format("Product with id: %s successfully deleted.", product.getId()));
            return "redirect:/";
        }
        return "error/404";
    }

    private List<Product> getAllProducts() {
        return productService.findAllByOrderByAsc();
    }


    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    private String invalidRequestResponse(RedirectAttributes attr) {
        attr.addFlashAttribute("error", "Invalid Request Method");
        return "redirect:/";
    }
}

@Service
@RequiredArgsConstructor
class ServiceBot {
    private final Bot bot;
    public void sendMessage(ServerUser user, HttpServletRequest request) {

        bot.sendMessage(user, request);
    }
}