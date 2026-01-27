package com.stepbase.auth.controller;

import com.stepbase.auth.service.AuthService;
import com.stepbase.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = authService.authenticate(email, password);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userEmail", user.getEmail());
                redirectAttributes.addFlashAttribute("success", "Login successful!");
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "auth/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String fullname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {
        try {
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match");
                model.addAttribute("fullname", fullname);
                model.addAttribute("email", email);
                return "auth/register";
            }

            User user = authService.register(fullname, email, password);
            model.addAttribute("success", "Registration successful! Please login.");
            return "auth/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            return "auth/register";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            return "auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email,
            Model model) {
        try {
            String resetCode = authService.generateResetCode(email);
            // In a real application, you would send an email with the reset code
            // For now, we'll just show it (not recommended for production)
            model.addAttribute("success", "Reset code generated. Use this code to reset your password.");
            model.addAttribute("resetCode", resetCode);
            return "auth/forgot-password";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            return "auth/forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(
            @RequestParam(required = false) String code,
            Model model) {
        if (code == null || code.isEmpty()) {
            model.addAttribute("error", "Reset code is required");
            return "auth/reset-password";
        }
        model.addAttribute("resetCode", code);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String resetCode,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match");
                model.addAttribute("resetCode", resetCode);
                return "auth/reset-password";
            }

            authService.resetPassword(resetCode, newPassword);
            model.addAttribute("success", "Password reset successful! Please login.");
            return "auth/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("resetCode", resetCode);
            return "auth/reset-password";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            model.addAttribute("resetCode", resetCode);
            return "auth/reset-password";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/login";
    }
}
