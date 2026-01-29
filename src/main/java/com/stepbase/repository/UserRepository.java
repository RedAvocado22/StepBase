package com.stepbase.repository;

import com.stepbase.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<com.stepbase.model.User> userRowMapper = new RowMapper<User>() {
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
    };

    public User save(User user) {
        if (user.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)",
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.getEmail()
            );
            Long id = jdbcTemplate.queryForObject(
                    "SELECT LAST_INSERT_ID()",
                    Long.class
            );
            user.setId(id);
        } else {
            jdbcTemplate.update(
                    "UPDATE users SET username = ?, password = ?, role = ?, email = ? WHERE id = ?",
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.getEmail(),
                    user.getId()
            );
        }
        return user;
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE username = ?",
                userRowMapper,
                username
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Collection<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id", userRowMapper);
    }

    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE id = ?",
                userRowMapper,
                id
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE email = ?",
                userRowMapper,
                email
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public void updatePassword(Long userId, String newPassword) {
        jdbcTemplate.update(
                "UPDATE users SET password = ? WHERE id = ?",
                newPassword,
                userId
        );
    }
}


