package com.stepbase.order.service;

import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import com.stepbase.order.repository.OrderDAO;
import com.stepbase.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService implements OrderRepository {
    @Autowired
    private OrderDAO orderDAO;
    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order findById(int id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<Order> findByUserId(int userId) {
        return orderDAO.findByUserId(userId);
    }

    @Override
    public void saveOrUpdate(Order order) {
        orderDAO.saveOrUpdate(order);
    }

    @Override
    public void delete(int id) {
        orderDAO.delete(id);
    }

    public void paymentOrder(int orderId, boolean paymentSuccess) {
        Order order=orderDAO.findById(orderId);
        if(order == null) {
            throw new RuntimeException("Order not found with ID: "+orderId);
        }
        if(paymentSuccess) {
            order.setPaid(true);
            order.setOrderStatus("PAID"); //PENDING,PAID,SHIPPED,DELIVERED,CANCELLED
            //update to db
            orderDAO.saveOrUpdate(order);
            System.out.println("DEBUG: the order " + order.getId() + " is paid.");
        } else {
            // Cập nhật trạng thái khi thanh toán thất bại
            order.setPaid(false);
            order.setOrderStatus("PENDING");
            orderDAO.saveOrUpdate(order);
            System.out.println("DEBUG: the order " + order.getId() + " is pending.");
        }
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderDAO.saveOrderItem(orderItem);
    }
}
