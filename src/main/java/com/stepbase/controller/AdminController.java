package com.stepbase.controller;


import com.stepbase.model.Order;
import com.stepbase.model.User;
import com.stepbase.repository.OrderRepository;
import com.stepbase.repository.UserRepository;
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

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            model.addAttribute("error", "Admin access required. Only administrators and staff can access this page.");
            return "redirect:/auth/login?error=Admin access required";
        }
        List<Order> allOrders = orderRepository.findAll();
        Collection<User> allUsers = userRepository.findAll();
        
        model.addAttribute("orderCount", allOrders.size());
        model.addAttribute("userCount", allUsers.size());
        model.addAttribute("orders", allOrders);
        
        if ("ADMIN".equals(currentUser.getRole())) {
            Map<String, Integer> orderStats = new HashMap<>();
            for (Order order : allOrders) {
                if (order.getCreatedAt() != null) {
                    String month = order.getCreatedAt().getMonth().toString() + " " + order.getCreatedAt().getYear();
                    orderStats.put(month, orderStats.getOrDefault(month, 0) + 1);
                }
            }
            model.addAttribute("orderStats", orderStats);
            
            Map<String, Long> userStats = allUsers.stream()
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
            model.addAttribute("userStats", userStats);
        }
        
        return "admin-dashboard";
    }


}


