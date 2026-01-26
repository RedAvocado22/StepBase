package com.stepbase.admin.controller;

import com.stepbase.user.User;
import com.stepbase.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "20") int size,
                            Model model) {
        List<User> users = userService.listUsers(page, size);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", userService.countAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String addForm() {
        return "user/add";
    }

    @PostMapping("/add")
    public String addStaff(@RequestParam String fullname,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        try {
            userService.createStaff(fullname, email, password);
            return "redirect:/admin/users";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/add";
        }
    }
}