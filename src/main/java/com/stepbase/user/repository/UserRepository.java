package com.stepbase.user.repository;

import com.stepbase.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setFullname(rs.getString("fullname"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhoneNumber(rs.getString("phone_number"));
        u.setGender(rs.getInt("gender"));
        u.setIsActive(rs.getInt("is_active"));
        u.setAdmin(rs.getBoolean("is_admin"));
        u.setResetCode(rs.getString("reset_code"));

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            u.setCreatedAt(created.toLocalDateTime());
        }
        u.setDeleted(rs.getBoolean("is_deleted"));
        return u;
    };

    public Optional<User> findById(int id) {
        List<User> list = jdbcTemplate.query(
                "SELECT id, fullname, email, password, phone_number, gender, is_active, is_admin, reset_code, created_at, is_deleted FROM users WHERE id = ?",
                rowMapper,
                id
        );
        return list.stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        List<User> list = jdbcTemplate.query(
                "SELECT id, fullname, email, password, phone_number, gender, is_active, is_admin, reset_code, created_at, is_deleted FROM users WHERE email = ? LIMIT 1",
                rowMapper,
                email
        );
        return list.stream().findFirst();
    }

    public List<User> findAll(int offset, int limit) {
        return jdbcTemplate.query(
                "SELECT id, fullname, email, password, phone_number, gender, is_active, is_admin, reset_code, created_at, is_deleted FROM users ORDER BY id DESC LIMIT ? OFFSET ?",
                rowMapper,
                limit,
                offset
        );
    }

    public int countAll() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        return count == null ? 0 : count;
    }

    public User save(User user) {
        if (user.getId() <= 0) {
            return insert(user);
        }
        update(user);
        return user;
    }

    private User insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (fullname, email, password, phone_number, gender, is_active, is_admin, reset_code, created_at, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setInt(5, user.getGender());
            ps.setInt(6, user.getIsActive());
            ps.setBoolean(7, user.isAdmin());
            ps.setString(8, user.getResetCode());

            LocalDateTime createdAt = user.getCreatedAt();
            ps.setTimestamp(9, createdAt == null ? null : Timestamp.valueOf(createdAt));
            ps.setBoolean(10, user.isDeleted());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            user.setId(key.intValue());
        }
        return user;
    }

    private void update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET fullname = ?, email = ?, password = ?, phone_number = ?, gender = ?, is_active = ?, is_admin = ?, reset_code = ?, is_deleted = ? WHERE id = ?",
                user.getFullname(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getIsActive(),
                user.isAdmin(),
                user.getResetCode(),
                user.isDeleted(),
                user.getId()
        );
    }

    public boolean deleteById(int id) {
        int affected = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
        return affected > 0;
    }
}
