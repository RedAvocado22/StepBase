package com.stepbase.controller;


import com.stepbase.model.CartItem;
import com.stepbase.model.Order;
import com.stepbase.model.Product;
import com.stepbase.model.User;
import com.stepbase.repository.OrderRepository;
import com.stepbase.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @SuppressWarnings("unchecked")
    private static List<CartItem> getCart(HttpSession session) {
        Object existing = session.getAttribute("cart");
        if (existing == null) {
            List<CartItem> cart = new ArrayList<>();
            session.setAttribute("cart", cart);
            return cart;
        }
        return (List<CartItem>) existing;
    }

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartController(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cart = getCart(session);
        model.addAttribute("items", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        List<CartItem> cart = getCart(session);
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return "redirect:/cart";
            }
        }
        cart.add(new CartItem(product, quantity));
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "Please login before checkout.");
            return "login";
        }
        List<CartItem> cart = getCart(session);
        if (cart.isEmpty()) {
            model.addAttribute("error", "Your cart is empty.");
            return "cart";
        }
        Order order = new Order();
        order.setCustomer(currentUser);
        order.setItems(new ArrayList<>(cart));
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        cart.clear();
        model.addAttribute("message", "Order placed successfully.");
        return "cart";
    }
}


