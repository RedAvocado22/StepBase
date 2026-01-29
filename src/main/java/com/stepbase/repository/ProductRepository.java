package com.stepbase.repository;

import com.stepbase.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            return product;
        }
    };

    public Product save(Product product) {
        if (product.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                    product.getName(),
                    product.getDescription(),
                    product.getPrice()
            );
            Long id = jdbcTemplate.queryForObject(
                    "SELECT LAST_INSERT_ID()",
                    Long.class
            );
            product.setId(id);
        } else {
            jdbcTemplate.update(
                    "UPDATE products SET name = ?, description = ?, price = ? WHERE id = ?",
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getId()
            );
        }
        return product;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products ORDER BY id", productRowMapper);
    }

    public Optional<Product> findById(Long id) {
        List<Product> products = jdbcTemplate.query(
                "SELECT * FROM products WHERE id = ?",
                productRowMapper,
                id
        );
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }
}


