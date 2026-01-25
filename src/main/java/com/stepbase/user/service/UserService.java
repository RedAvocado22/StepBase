package com.stepbase.user.service;

import com.stepbase.user.User;
import com.stepbase.user.repository.UserDAO;
import com.stepbase.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements UserRepository {
    @Autowired
    private UserDAO userDAO;
    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User update(User user) {
        return userDAO.update(user);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }
}
