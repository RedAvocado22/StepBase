package com.stepbase;

import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import com.stepbase.order.repository.OrderDAO;
import com.stepbase.product.Product;
import com.stepbase.product.metadata.Brand;
import com.stepbase.product.metadata.Category;
import com.stepbase.product.metadata.Color;
import com.stepbase.product.repository.ProductDAO;
import com.stepbase.user.User;
import com.stepbase.user.repository.UserDAO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void  main(String[] args) {
        OrderDAO da = new OrderDAO();
        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();

        User user = new User(0, "Nguyễn Văn A", "vana@gmail.com", "password123",
                "0912345678", 1, 1, false, null, LocalDateTime.now(), false);

        //userDAO.save(user);

        Set<OrderItem> items = new HashSet<>();
        Order myOrder = new Order(0, "Nguyễn Văn A", "0901234567", "123 Đường ABC, Hà Nội",
                "PENDING", LocalDateTime.now(), new BigDecimal("1500000.00"),
                false, user, items);

        //da.saveOrUpdate(myOrder);
        Brand brand = new Brand(1, "Nike", "nike_logo.png", false);
        Category category = new Category(1, "Giày chạy bộ", "cat_shoes.png", false);
        Color color = new Color(1, "Trắng", "#FFFFFF", false);

// 2. Khởi tạo Product bằng Constructor full tham số (theo thứ tự khai báo trong class)
        Product product = new Product(
                0,                                  // id
                "Nike Air Force 1",                 // name
                "/images/af1.jpg",                  // imageUrl
                new BigDecimal("2500000.00"),       // basePrice
                brand,                              // brand (Object)
                category,                           // category (Object)
                color,                              // color (Object)
                new HashSet<>()                     // variants (Set trống)
        );
        productDAO.saveOrUpdate(product);
    }
}
