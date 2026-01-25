package com.stepbase.user.repository;

import com.stepbase.user.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(int id);
    List<User> findAll();
    User update(User user);
    void delete(int id);
}
