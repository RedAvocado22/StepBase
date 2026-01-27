package com.stepbase.config;

import com.stepbase.order.repository.OrderDAO;
import com.stepbase.order.service.OrderService;
import com.stepbase.product.repository.ProductDAO;
import com.stepbase.product.service.ProductService;
import com.stepbase.user.repository.UserDAO;
import com.stepbase.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.stepbase")
@Configuration
public class AppConfig {

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAO();
    }
    @Bean
    public OrderService orderService() {
        return new OrderService();
    }
    @Bean
    public UserDAO userDAO() {
        return  new UserDAO();
    }
    @Bean
    public UserService userService() {
        return new UserService();
    }
    @Bean
    public ProductDAO productDAO() {
        return new ProductDAO();
    }
    @Bean
    public ProductService productService() {
        return new ProductService();
    }
}
