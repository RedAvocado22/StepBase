package com.stepbase.order.repository;

import com.stepbase.order.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll();
    Order findById(int id);
    List<Order> findByUserId(int userId);
    void saveOrUpdate(Order order);
    void delete(int id);
}
