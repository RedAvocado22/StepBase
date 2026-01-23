package com.stepbase.product.repository;

import com.stepbase.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> rowMapper = (rs, rowNum) -> {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setImageUrl(rs.getString("image_url"));
        BigDecimal basePrice = rs.getBigDecimal("base_price");
        p.setBasePrice(basePrice);
        // Note: brand/category/color/variants not auto-loaded (no JPA)
        return p;
    };

    public Optional<Product> findById(int id) {
        List<Product> list = jdbcTemplate.query(
                "SELECT id, name, image_url, base_price FROM products WHERE id = ?",
                rowMapper,
                id
        );
        return list.stream().findFirst();
    }

    public List<Product> findAll(int offset, int limit) {
        return jdbcTemplate.query(
                "SELECT id, name, image_url, base_price FROM products ORDER BY id DESC LIMIT ? OFFSET ?",
                rowMapper,
                limit,
                offset
        );
    }

    public List<Product> searchByName(String keyword, int offset, int limit) {
        String like = keyword == null ? "%" : ("%" + keyword + "%");
        return jdbcTemplate.query(
                "SELECT id, name, image_url, base_price FROM products WHERE name LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?",
                rowMapper,
                like,
                limit,
                offset
        );
    }

    public int countAll() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        return count == null ? 0 : count;
    }

    public Product save(Product product) {
        if (product.getId() <= 0) {
            return insert(product);
        }
        update(product);
        return product;
    }

    private Product insert(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO products (name, image_url, base_price, brand_id, category_id, color_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setBigDecimal(3, product.getBasePrice());

            // FK columns are nullable in schema, set if present
            ps.setObject(4, product.getBrand() == null ? null : product.getBrand().getId(), java.sql.Types.INTEGER);
            ps.setObject(5, product.getCategory() == null ? null : product.getCategory().getId(), java.sql.Types.INTEGER);
            ps.setObject(6, product.getColor() == null ? null : product.getColor().getId(), java.sql.Types.INTEGER);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            product.setId(key.intValue());
        }
        return product;
    }

    private void update(Product product) {
        jdbcTemplate.update(
                "UPDATE products SET name = ?, image_url = ?, base_price = ?, brand_id = ?, category_id = ?, color_id = ? WHERE id = ?",
                product.getName(),
                product.getImageUrl(),
                product.getBasePrice(),
                product.getBrand() == null ? null : product.getBrand().getId(),
                product.getCategory() == null ? null : product.getCategory().getId(),
                product.getColor() == null ? null : product.getColor().getId(),
                product.getId()
        );
    }

    public boolean deleteById(int id) {
        int affected = jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
        return affected > 0;
    }
}
