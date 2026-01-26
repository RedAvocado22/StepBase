package com.stepbase.user.service;

import com.stepbase.user.User;
import com.stepbase.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
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
}
