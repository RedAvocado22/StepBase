package com.stepbase.order.repository;

import com.stepbase.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Order> rowMapper = (rs, rowNum) -> {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setConsigneeName(rs.getString("consignee_name"));
        o.setPhone(rs.getString("phone_number"));
        o.setShippingAddress(rs.getString("shipping_address"));
        o.setOrderStatus(rs.getString("order_status"));

        Timestamp ts = rs.getTimestamp("order_date");
        if (ts != null) {
            o.setOrderDate(ts.toLocalDateTime());
        }

        BigDecimal total = rs.getBigDecimal("total_amount");
        o.setTotalAmount(total);
        o.setPaid(rs.getBoolean("is_paid"));

        // Note: user and items are not auto-loaded (no JPA). Load them explicitly if needed.
        return o;
    };

    public Optional<Order> findById(int id) {
        List<Order> list = jdbcTemplate.query(
                "SELECT id, consignee_name, phone_number, shipping_address, order_status, order_date, total_amount, is_paid FROM orders WHERE id = ?",
                rowMapper,
                id
        );
        return list.stream().findFirst();
    }

    public List<Order> findAll(int offset, int limit) {
        return jdbcTemplate.query(
                "SELECT id, consignee_name, phone_number, shipping_address, order_status, order_date, total_amount, is_paid FROM orders ORDER BY id DESC LIMIT ? OFFSET ?",
                rowMapper,
                limit,
                offset
        );
    }

    public List<Order> findByUserId(int userId, int offset, int limit) {
        return jdbcTemplate.query(
                "SELECT id, consignee_name, phone_number, shipping_address, order_status, order_date, total_amount, is_paid FROM orders WHERE user_id = ? ORDER BY id DESC LIMIT ? OFFSET ?",
                rowMapper,
                userId,
                limit,
                offset
        );
    }

    public int countAll() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
        return count == null ? 0 : count;
    }

    public int countByUserId(int userId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders WHERE user_id = ?", Integer.class, userId);
        return count == null ? 0 : count;
    }

    /**
     * save: insert if id==0, else update.
     * Note: requires order.user to be present on insert (for user_id).
     */
    public Order save(Order order) {
        if (order.getId() <= 0) {
            return insert(order);
        }
        update(order);
        return order;
    }

    private Order insert(Order order) {
        if (order.getUser() == null) {
            throw new IllegalArgumentException("order.user must not be null when inserting (user_id is NOT NULL)");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orders (user_id, consignee_name, phone_number, shipping_address, order_status, order_date, total_amount, is_paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, order.getUser().getId());
            ps.setString(2, order.getConsigneeName());
            ps.setString(3, order.getPhone());
            ps.setString(4, order.getShippingAddress());
            ps.setString(5, order.getOrderStatus());
            LocalDateTime od = order.getOrderDate();
            ps.setTimestamp(6, od == null ? null : Timestamp.valueOf(od));
            ps.setBigDecimal(7, order.getTotalAmount());
            ps.setBoolean(8, order.isPaid());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            order.setId(key.intValue());
        }
        return order;
    }

    private void update(Order order) {
        jdbcTemplate.update(
                "UPDATE orders SET consignee_name = ?, phone_number = ?, shipping_address = ?, order_status = ?, order_date = ?, total_amount = ?, is_paid = ? WHERE id = ?",
                order.getConsigneeName(),
                order.getPhone(),
                order.getShippingAddress(),
                order.getOrderStatus(),
                order.getOrderDate() == null ? null : Timestamp.valueOf(order.getOrderDate()),
                order.getTotalAmount(),
                order.isPaid(),
                order.getId()
        );
    }

    public boolean deleteById(int id) {
        int affected = jdbcTemplate.update("DELETE FROM orders WHERE id = ?", id);
        return affected > 0;
    }
}
