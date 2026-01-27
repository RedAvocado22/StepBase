package com.stepbase.order.repository;

import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class OrderDAO {
    private static final EntityManagerFactory
            emf= Persistence.createEntityManagerFactory("JPAs");

    public OrderDAO() {
    }

    /**
     * Lấy danh sách tất cả đơn hàng bằng JPQL
     */
    public List<Order> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String str="select o from Order o";
            return em.createQuery(str, Order.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm đơn hàng theo ID
     */
    public Order findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Order.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Xóa đơn hàng theo ID
     */
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Order order = em.find(Order.class, id);
            if (order != null) {
                em.remove(order);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm đơn hàng theo ID người dùng bằng Native Query
     */
    public List<Order> findByUserId(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            // Sử dụng tên cột "user_id" thực tế trong DB
            String jpql = "SELECT o FROM Order o WHERE o.user.id = :uId";
            return em.createQuery(jpql, Order.class)
                    .setParameter("uId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Lưu một đơn hàng mới hoặc cập nhật đơn hàng đã tồn tại
     */
    public void saveOrUpdate(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            if (order.getId() == 0) {
                em.persist(order); // Tạo mới
            } else {
                em.merge(order);   // Cập nhật
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * PHƯƠNG THỨC MỚI: Lưu trực tiếp từng OrderItem
     * Giúp ngắt hoàn toàn lỗi StackOverflowError vì không thông qua Set của Order
     */
    public void saveOrderItem(OrderItem item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            if (item.getId() == 0) {
                em.persist(item);
            } else {
                em.merge(item);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
