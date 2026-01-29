package com.stepbase.controller;


import com.stepbase.model.Order;
import com.stepbase.model.User;
import com.stepbase.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String orderHistory(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "Please login to view your orders.");
            return "login";
        }
        List<Order> orders = orderRepository.findByCustomer(currentUser);
        model.addAttribute("orders", orders);
        return "order-history";
    }
}


