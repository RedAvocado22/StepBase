package com.stepbase.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        try {
            logger.info("Initializing database tables and dummy data...");

            createTables();

            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            if (userCount == null || userCount == 0) {
                insertDummyData();
                logger.info("Dummy data inserted successfully!");
            } else {
                logger.info("Database already contains data, skipping dummy data insertion.");
            }

        } catch (Exception e) {
            logger.error("Error initializing database: " + e.getMessage(), e);
        }
    }

    private void createTables() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "username VARCHAR(50) UNIQUE NOT NULL, " +
                            "password VARCHAR(255) NOT NULL, " +
                            "role VARCHAR(20) NOT NULL, " +
                            "email VARCHAR(100)" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "description TEXT, " +
                            "price DECIMAL(10, 2) NOT NULL" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "customer_id BIGINT NOT NULL, " +
                            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                            "FOREIGN KEY (customer_id) REFERENCES users(id)" +
                            ")"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS order_items (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "order_id BIGINT NOT NULL, " +
                            "product_id BIGINT NOT NULL, " +
                            "quantity INT NOT NULL, " +
                            "price DECIMAL(10, 2) NOT NULL, " +
                            "FOREIGN KEY (order_id) REFERENCES orders(id), " +
                            "FOREIGN KEY (product_id) REFERENCES products(id)" +
                            ")"
            );

            logger.info("Database tables created successfully!");
        }
    }

    private void insertDummyData() {
        jdbcTemplate.update(
                "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)",
                "admin", "admin", "ADMIN", "admin@example.com"
        );
        jdbcTemplate.update(
                "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)",
                "staff", "staff", "STAFF", "staff@example.com"
        );
        jdbcTemplate.update(
                "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)",
                "customer1", "password", "CUSTOMER", "customer1@example.com"
        );
        jdbcTemplate.update(
                "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)",
                "customer2", "password", "CUSTOMER", "customer2@example.com"
        );

        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Laptop", "High-performance laptop with 16GB RAM and 512GB SSD", 999.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Smartphone", "Latest smartphone with 128GB storage and 5G support", 699.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Wireless Headphones", "Premium noise-cancelling wireless headphones", 249.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Smart Watch", "Fitness tracker with heart rate monitor and GPS", 299.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Tablet", "10-inch tablet with 64GB storage, perfect for reading and browsing", 399.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Keyboard", "Mechanical gaming keyboard with RGB lighting", 129.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Mouse", "Wireless ergonomic mouse with precision tracking", 79.99
        );
        jdbcTemplate.update(
                "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                "Monitor", "27-inch 4K UHD monitor with HDR support", 449.99
        );

        jdbcTemplate.update(
                "INSERT INTO orders (customer_id) VALUES ((SELECT id FROM users WHERE username = ?))",
                "customer1"
        );
        Long order1Id = jdbcTemplate.queryForObject(
                "SELECT id FROM orders WHERE customer_id = (SELECT id FROM users WHERE username = ?) ORDER BY id DESC LIMIT 1",
                Long.class,
                "customer1"
        );

        jdbcTemplate.update(
                "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, (SELECT id FROM products WHERE name = ?), ?, ?)",
                order1Id, "Laptop", 1, 999.99
        );
        jdbcTemplate.update(
                "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, (SELECT id FROM products WHERE name = ?), ?, ?)",
                order1Id, "Wireless Headphones", 1, 249.99
        );

        jdbcTemplate.update(
                "INSERT INTO orders (customer_id) VALUES ((SELECT id FROM users WHERE username = ?))",
                "customer2"
        );
        Long order2Id = jdbcTemplate.queryForObject(
                "SELECT id FROM orders WHERE customer_id = (SELECT id FROM users WHERE username = ?) ORDER BY id DESC LIMIT 1",
                Long.class,
                "customer2"
        );

        jdbcTemplate.update(
                "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, (SELECT id FROM products WHERE name = ?), ?, ?)",
                order2Id, "Smartphone", 1, 699.99
        );
        jdbcTemplate.update(
                "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, (SELECT id FROM products WHERE name = ?), ?, ?)",
                order2Id, "Smart Watch", 1, 299.99
        );
    }
}

