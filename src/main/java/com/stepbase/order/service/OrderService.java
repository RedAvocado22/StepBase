package com.stepbase.order.service;

import com.stepbase.order.Order;
import com.stepbase.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
