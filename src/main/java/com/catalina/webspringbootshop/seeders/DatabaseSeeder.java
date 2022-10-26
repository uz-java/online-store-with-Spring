package com.catalina.webspringbootshop.seeders;

import com.catalina.webspringbootshop.config.Roles;
import com.catalina.webspringbootshop.entity.Category;
import com.catalina.webspringbootshop.entity.Order;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.entity.User;
import com.catalina.webspringbootshop.repository.CategoryRepository;
import com.catalina.webspringbootshop.repository.OrderRepository;
import com.catalina.webspringbootshop.repository.ProductRepository;
import com.catalina.webspringbootshop.repository.UserRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DatabaseSeeder {

    private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private JdbcTemplate jdbcTemplate;
    private CategoryRepository categoryRepository;
    private OrderRepository orderRepository;
    private Faker faker;
    private final int USERS_TO_CREATE = 20;
    private final int PRODUCTS_TO_CREATE = 10;
    private final float productPrice = 10.00f;
    private final String description = "The first-generation iPhone was extremely thin (only 11.6 mm thick but wider and longer than many comparable devices. The display area was a 3.5 inch-wide screen with a multitouch interface and unusually high resolution (160 pixels per inch).";
    private final int CATEGORY_TO_CREATE = 5;
    private final String DESCRIPTION = "The iPhone is a smartphone made by Apple that combines a computer and as of 2017, there were 2.2 million apps available for it through the Apple App Store, according to Statista.";

    @Autowired
    public DatabaseSeeder(
            UserRepository userRepository,
            JdbcTemplate jdbcTemplate,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void fill() {
        this.faker = generateFaker();
    }

    private Faker generateFaker() {
        return new Faker(new Locale("en-US"));
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
    /*    seedUsersTable();
        seedCategoryTable();
        seedProductTable();
        seedOrderTable();*/
    }

    DecimalFormat f = new DecimalFormat("##.00");

    private void seedUsersTable() {
        String sql = "SELECT username, email FROM users U WHERE U.username = \"asliddin\" OR " +
                "U.email = \"asliddintojaliyev88@gmail.com\" LIMIT 1";
        List<User> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (u == null || u.size() <= 0) {
            User user = new User("asliddin", "asliddintojaliyev88@gmail.com", new BCryptPasswordEncoder().encode("123"), Roles.ADMIN.toString());
            user.setRole(Roles.ADMIN.toString());
            userRepository.save(user);
            logger.info("Users Seeded");
        } else {
            logger.info("Stipo is already in db!");
        }
        int userSize = userRepository.findAll().size();
        if (userSize < 2) {
            for (int i = 0; i < this.USERS_TO_CREATE; i++) {
                User fake = new User(faker.name().username(), faker.internet().emailAddress(), new BCryptPasswordEncoder().encode(faker.name().name()), Roles.CUSTOMER.toString());
                userRepository.save(fake);
            }
            logger.info("More users added");
        } else {
            logger.info("Enough users!");
        }
    }

    private void seedCategoryTable() {
        int categorySize = categoryRepository.findAll().size();
        if (categorySize < 2) {
            for (int i = 0; i < this.CATEGORY_TO_CREATE; i++) {
                Category category = new Category(this.faker.name().name());
                categoryRepository.save(category);
            }
            logger.info("More categories added");
        } else {
            logger.info("Enough categories!");

        }
    }

    private void seedProductTable() {

        int productSize = productRepository.findAll().size();
        if (productSize < 2) {
            for (int i = 0; i < this.PRODUCTS_TO_CREATE; i++) {
                Category category = categoryRepository.findAll().get(0);
                Product product = new Product(faker.pokemon().name() + i, 10.00f, 10, DESCRIPTION, category);

                productRepository.save(product);
            }
            logger.info("Products added!");
        } else {
            logger.info("Enough products!");
        }
    }

    private void seedOrderTable() {
        List<Product> a = productRepository.findAll();

        Order order = new Order(userRepository.findById(1), 10, 10.1f, new Date(), a);
        orderRepository.save(order);

    }
}
