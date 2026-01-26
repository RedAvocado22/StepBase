package com.stepbase.order.controller;

import com.stepbase.order.Order;
import com.stepbase.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/staff/orders")
    public String listOrders(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "20") int size,
                             Model model) {
        List<Order> orders = orderService.listOrders(page, size);
        model.addAttribute("orders", orders);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", orderService.countAll());
        return "order/list";
    }
}
