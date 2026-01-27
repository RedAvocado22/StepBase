package com.stepbase.auth.service;

import com.stepbase.user.User;
import com.stepbase.user.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        if (user.getIsActive() != 1 || user.isDeleted()) {
            return Optional.empty();
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Transactional
    public User register(String fullname, String email, String password) {
        if (fullname == null || fullname.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        if (userRepository.findByEmail(email.trim()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFullname(fullname.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setIsActive(1);
        user.setAdmin(false);
        user.setDeleted(false);
        user.setCreatedAt(java.time.LocalDateTime.now());

        return userRepository.save(user);
    }

    @Transactional
    public String generateResetCode(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email.trim().toLowerCase());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        User user = userOpt.get();
        String resetCode = UUID.randomUUID().toString().replace("-", "");
        user.setResetCode(resetCode);
        userRepository.save(user);

        return resetCode;
    }

    public Optional<User> findByResetCode(String resetCode) {
        return userRepository.findByResetCode(resetCode);
    }

    @Transactional
    public void resetPassword(String resetCode, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        Optional<User> userOpt = userRepository.findByResetCode(resetCode);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid reset code");
        }

        User user = userOpt.get();
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        user.setResetCode(null); // Clear reset code after use
        userRepository.save(user);
    }
}
