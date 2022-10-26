package com.catalina.webspringbootshop;

import com.catalina.webspringbootshop.entity.Category;
import com.catalina.webspringbootshop.entity.Product;
import com.catalina.webspringbootshop.repository.CategoryRepository;
import com.catalina.webspringbootshop.repository.ProductRepository;
import com.github.javafaker.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class WebSpringBootShopApplication {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebSpringBootShopApplication.class, args);
    }

    @PostConstruct
    public void initialize() {

        Faker faker = new Faker();


        Category Cbook = categoryRepository.save(new Category("book"));
        Category Capp = categoryRepository.save(new Category("app"));
        Category Cfood = categoryRepository.save(new Category("food"));

        Category Cmusic = categoryRepository.save(new Category("music"));


        for (int i = 0; i < 5; i++) {
            Book book = faker.book();
            App app = faker.app();
            Food food = faker.food();
            Music music = faker.music();
            float minX = 0.0f;
            float maxX = 100.0f;

            Random rand = new Random();

            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            try {
                productRepository.saveAll(List.of(
                        Product.builder()
                                .name(book.title())
                                .unit_in_stock(0)
                                .description(book.author())
                                .category(Cbook)
                                .price(finalX)
                                .build(), Product.builder()

                                .name(app.name())
                                .unit_in_stock(0)
                                .description(app.version())
                                .category(Capp)
                                .price(rand.nextFloat() * (maxX - minX) + minX)
                                .build(),

                        Product.builder()

                                .name(food.dish())
                                .unit_in_stock(0)
                                .description(food.dish())
                                .category(Cfood)
                                .price(rand.nextFloat() * (maxX - minX) + minX)
                                .build(),
                        Product.builder()

                                .name(music.chord())
                                .unit_in_stock(0)
                                .description(music.genre())
                                .category(Cmusic)
                                .price(rand.nextFloat() * (maxX - minX) + minX)
                                .build()
                ));
            } catch (Exception e) {
                e.getCause();
            }
        }
    }
}
