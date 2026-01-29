package com.stepbase.controller;


import com.stepbase.model.User;
import com.stepbase.repository.UserRepository;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("currentUser", user);
            if ("ADMIN".equals(user.getRole()) || "STAFF".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/products";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        user.setRole("CUSTOMER");
        userRepository.save(user);
        model.addAttribute("message", "Registration successful, please login.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(Model model) {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String doResetPassword(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String newPassword,
                                  Model model) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getEmail() != null && user.getEmail().equals(email)) {
                userRepository.updatePassword(user.getId(), newPassword);
                model.addAttribute("message", "Password reset successful. Please login with your new password.");
                return "login";
            } else {
                model.addAttribute("error", "Email does not match the username.");
            }
        } else {
            model.addAttribute("error", "Username not found.");
        }
        return "reset-password";
    }

}


