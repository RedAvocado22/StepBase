package com.stepbase.order.controller;

import com.stepbase.cart.Cart;
import com.stepbase.cart.CartItem;
import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import com.stepbase.order.service.OrderService;
import com.stepbase.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // =======================
    // STAFF ORDER LISTING (from main)
    // =======================
    @GetMapping("/staff/orders")
    public String listOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "size", defaultValue = "20") int size,
                             Model model) {
        List<Order> orders = orderService.listOrders(page, size);
        model.addAttribute("orders", orders);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", orderService.countAll());
        return "order/list";
    }

    // =======================
    // ORDER PAYMENT (from duong)
    // =======================
    @GetMapping("/payment")
    public String showPaymentForm(@RequestParam("message") boolean meString,
                                  Model model) {
        if(meString) {
            model.addAttribute("message", "Sai định dạng số.");
        }
        return "paymentForm";
    }

    @PostMapping("/payment")
    public String handlePayment(@RequestParam("orderId") String orderIdParam,
                                @RequestParam("paymentSuccess") boolean success,
                                Model model) {
        //validate input
        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            return "redirect:/orders/payment?message=true";
        }
        //service layer
        try {
            orderService.paymentOrder(orderId, success);
            model.addAttribute("message",
                    "Thanh toán thành công đơn hàng có ID: "+orderId);
            return "orderHistory";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "errorPage";
        }
    }

    // =======================
    // ORDER HISTORY (from duong)
    // =======================
    @GetMapping("/history")
    public String orderHistory(@RequestParam(name = "userId", defaultValue = "1") int userId,
                               Model model) {
        model.addAttribute("orders",
                orderService.findByUserId(userId));
        return  "orderHistory";
    }

    // =======================
    // ORDER CHECKOUT (from duong)
    // =======================
    @GetMapping("/checkout")
    public String showCheckoutForm(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        return "paymentForm";
    }

    @PostMapping("/place-order")
    public String placeOrder(
            @RequestParam("consigneeName") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session
    ) {
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null || cart.isEmpty()) return "redirect:/cart";

        // BƯỚC 1: Lưu Order trước để lấy ID
        Order order = new Order();
        order.setUser(userService.findById(1).orElseThrow(() -> new RuntimeException("User not found")));
        order.setConsigneeName(name);
        order.setPhone(phone);
        order.setShippingAddress(address);
        order.setTotalAmount(cart.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        order.setPaid(false);

        orderService.saveOrUpdate(order);

        // BƯỚC 2: Lưu từng OrderItem độc lập
        // Việc này giúp tránh dùng HashSet -> Tránh gọi hashCode() -> Hết StackOverflow
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setQuantity(cartItem.getQuantity());
            oi.setPriceAtPurchase(cartItem.getProduct().getBasePrice());
            oi.setOrder(order); // Liên kết ID
            oi.setVariant(null); // Tránh lỗi Lazy vì chưa có dữ liệu variant
            orderService.saveOrderItem(oi); // Lưu trực tiếp item
        }
        try {
            // BƯỚC 3: Thanh toán
            orderService.paymentOrder(order.getId(), true);
            // BƯỚC 4: Hoàn tất
            cart.clear();
            return "redirect:/cart";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cart/errors?error=true";
        }
    }
}
