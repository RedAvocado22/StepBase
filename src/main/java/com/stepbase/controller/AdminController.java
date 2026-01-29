package com.hsf.assignment1.shop.controller;

import com.hsf.assignment1.shop.model.Order;
import com.hsf.assignment1.shop.model.User;
import com.hsf.assignment1.shop.repository.OrderRepository;
import com.hsf.assignment1.shop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public AdminController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            return "redirect:/auth/login?error=Admin access required";
        }
        List<Order> allOrders = orderRepository.findAll();
        model.addAttribute("orders", allOrders);
        return "admin-orders";
    }

    @GetMapping("/users")
    public String viewUsers(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/auth/login?error=Admin access required";
        }
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @GetMapping("/staff/add")
    public String showAddStaff(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/auth/login?error=Admin access required";
        }
        model.addAttribute("user", new User());
        return "staff-form";
    }

    @PostMapping("/staff/add")
    public String addStaff(@ModelAttribute User user, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/auth/login?error=Admin access required";
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("user", user);
            return "staff-form";
        }
        user.setRole("STAFF");
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}


