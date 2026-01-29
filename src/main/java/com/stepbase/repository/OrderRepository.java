package com.stepbase.repository;


import com.stepbase.model.CartItem;
import com.stepbase.model.Order;
import com.stepbase.model.Product;
import com.stepbase.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            Timestamp createdAt = order.getCreatedAt() != null
                    ? Timestamp.valueOf(order.getCreatedAt())
                    : Timestamp.valueOf(LocalDateTime.now());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO orders (customer_id, created_at) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, order.getCustomer().getId());
                ps.setTimestamp(2, createdAt);
                return ps;
            }, keyHolder);

            Long orderId = keyHolder.getKey().longValue();
            order.setId(orderId);

            if (order.getItems() != null && !order.getItems().isEmpty()) {
                for (CartItem item : order.getItems()) {
                    jdbcTemplate.update(
                            "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)",
                            orderId,
                            item.getProduct().getId(),
                            item.getQuantity(),
                            item.getProduct().getPrice()
                    );
                }
            }
        } else {
            jdbcTemplate.update("DELETE FROM order_items WHERE order_id = ?", order.getId());

            if (order.getItems() != null && !order.getItems().isEmpty()) {
                for (CartItem item : order.getItems()) {
                    jdbcTemplate.update(
                            "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)",
                            order.getId(),
                            item.getProduct().getId(),
                            item.getQuantity(),
                            item.getProduct().getPrice()
                    );
                }
            }
        }
        return order;
    }

    public List<Order> findByCustomer(User customer) {
        List<Order> orders = jdbcTemplate.query(
                "SELECT * FROM orders WHERE customer_id = ? ORDER BY created_at DESC",
                new OrderRowMapper(),
                customer.getId()
        );

        for (Order order : orders) {
            loadOrderItems(order);
        }

        return orders;
    }

    public List<Order> findAll() {
        List<Order> orders = jdbcTemplate.query(
                "SELECT * FROM orders ORDER BY created_at DESC",
                new OrderRowMapper()
        );

        for (Order order : orders) {
            loadOrderItems(order);
        }

        return orders;
    }

    private void loadOrderItems(Order order) {
        List<CartItem> items = jdbcTemplate.query(
                "SELECT oi.*, p.id as product_id, p.name, p.description, p.price as product_price " +
                        "FROM order_items oi " +
                        "JOIN products p ON oi.product_id = p.id " +
                        "WHERE oi.order_id = ?",
                new CartItemRowMapper(),
                order.getId()
        );
        order.setItems(items);
    }

    private class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));

            Long customerId = rs.getLong("customer_id");
            User customer = jdbcTemplate.query(
                    "SELECT * FROM users WHERE id = ?",
                    new UserRowMapper(),
                    customerId
            ).get(0);
            order.setCustomer(customer);

            Timestamp timestamp = rs.getTimestamp("created_at");
            if (timestamp != null) {
                order.setCreatedAt(timestamp.toLocalDateTime());
            }

            order.setItems(new ArrayList<>());
            return order;
        }
    }

    private class CartItemRowMapper implements RowMapper<CartItem> {
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("product_id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("product_price"));

            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(rs.getInt("quantity"));
            return item;
        }
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}


