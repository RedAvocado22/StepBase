package com.stepbase.order.service;

import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import com.stepbase.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> listOrders(int page, int size) {
        int offset = Math.max(0, (page - 1) * size);
        return orderRepository.findAll(offset, size);
    }

    public long countAll() {
        return orderRepository.countAll();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public List<Order> findByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public void saveOrUpdate(Order order) {
        orderRepository.saveOrUpdate(order);
    }

    @Transactional
    public void delete(int id) {
        orderRepository.delete(id);
    }

    @Transactional
    public void paymentOrder(int orderId, boolean paymentSuccess) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        if (paymentSuccess) {
            order.setPaid(true);
            order.setOrderStatus("PAID"); //PENDING,PAID,SHIPPED,DELIVERED,CANCELLED
            //update to db
            orderRepository.saveOrUpdate(order);
            System.out.println("DEBUG: the order " + order.getId() + " is paid.");
        } else {
            // Cập nhật trạng thái khi thanh toán thất bại
            order.setPaid(false);
            order.setOrderStatus("PENDING");
            orderRepository.saveOrUpdate(order);
            System.out.println("DEBUG: the order " + order.getId() + " is pending.");
        }
    }

    @Transactional
    public void saveOrderItem(OrderItem orderItem) {
        orderRepository.saveOrderItem(orderItem);
    }
}
