package com.stepbase.user.service;

import com.stepbase.user.User;
import com.stepbase.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        return userRepository.update(user);
    }

    @Transactional
    public void updateProfile(int userId, String fullname, String phoneNumber, int gender) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String fn = fullname == null ? "" : fullname.trim();
        if (fn.isEmpty()) {
            throw new IllegalArgumentException("fullname must not be empty");
        }

        user.setFullname(fn);
        user.setPhoneNumber(phoneNumber == null ? null : phoneNumber.trim());
        user.setGender(gender);

        userRepository.save(user);
    }

    public List<User> listUsers(int page, int size) {
        int offset = Math.max(0, (page - 1) * size);
        return userRepository.findAll(offset, size);
    }

    public long countAll() {
        return userRepository.countAll();
    }

    @Transactional
    public User createStaff(String fullname, String email, String password) {
        if (fullname == null || fullname.trim().isEmpty()) {
            throw new IllegalArgumentException("fullname must not be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("email must not be empty");
        }
        if (userRepository.findByEmail(email.trim()).isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }
        User u = new User();
        u.setFullname(fullname.trim());
        u.setEmail(email.trim());
        u.setPassword(password == null ? "" : password);
        u.setIsActive(1);
        u.setAdmin(false);
        return userRepository.save(u);
    }

    @Transactional
    public void delete(int id) {
        userRepository.delete(id);
    }
}
