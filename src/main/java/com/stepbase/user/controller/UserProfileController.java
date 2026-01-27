package com.stepbase.user.controller;

import com.stepbase.user.User;
import com.stepbase.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        userService.findById(userId).ifPresent(user -> {
            model.addAttribute("user", user);
        });
        
        return "user/profile";
    }

    @PostMapping
    public String updateProfile(
            @RequestParam(name = "fullname") String fullname,
            @RequestParam(name = "phoneNumber") String phoneNumber,
            @RequestParam(name = "gender", required = false, defaultValue = "0") int gender,
            HttpSession session,
            RedirectAttributes redirectAttrs) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        try {
            userService.updateProfile(userId, fullname, phoneNumber, gender);
            redirectAttrs.addFlashAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        
        return "redirect:/profile";
    }
}