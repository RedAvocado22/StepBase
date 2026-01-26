package com.stepbase.admin.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final JdbcTemplate jdbcTemplate;

    public AdminDashboardService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getStats() {
        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM users WHERE is_deleted = 0) AS user_count,
                    (SELECT COUNT(*) FROM products) AS product_count,
                    (SELECT COUNT(*) FROM orders) AS order_count,
                    (SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE is_paid = 1) AS paid_revenue
                """;

        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> getRecentOrders(int limit) {
        String sql = """
                SELECT o.id,
                       o.order_date,
                       o.total_amount,
                       o.order_status,
                       o.is_paid,
                       u.fullname AS customer_name,
                       u.email AS customer_email
                FROM orders o
                JOIN users u ON u.id = o.user_id
                ORDER BY o.order_date DESC
                LIMIT ?
                """;
        return jdbcTemplate.queryForList(sql, limit);
    }

    public List<Map<String, Object>> getLowStockVariants(int limit) {
        String sql = """
                SELECT pv.id AS variant_id,
                       p.id AS product_id,
                       p.name AS product_name,
                       s.size_eu AS size_eu,
                       pv.stock_quantity
                FROM product_variants pv
                JOIN products p ON p.id = pv.product_id
                JOIN sizes s ON s.id = pv.size_id
                ORDER BY pv.stock_quantity ASC, p.id ASC
                LIMIT ?
                """;
        return jdbcTemplate.queryForList(sql, limit);
    }

    public BigDecimal getPaidRevenue() {
        BigDecimal revenue = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE is_paid = 1",
                BigDecimal.class
        );
        return revenue == null ? BigDecimal.ZERO : revenue;
    }
}
