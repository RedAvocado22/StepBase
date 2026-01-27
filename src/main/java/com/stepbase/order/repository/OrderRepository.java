package com.stepbase.order.repository;

import com.stepbase.order.Order;
import com.stepbase.order.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Order> findById(int id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    public List<Order> findAll(int offset, int limit) {
        return em.createQuery("SELECT o FROM Order o ORDER BY o.id DESC", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o ORDER BY o.id DESC", Order.class)
                .getResultList();
    }

    public List<Order> findByUserId(int userId, int offset, int limit) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user.id = :uid ORDER BY o.id DESC", Order.class)
                .setParameter("uid", userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findByUserId(int userId) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user.id = :uid ORDER BY o.id DESC", Order.class)
                .setParameter("uid", userId)
                .getResultList();
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
    }

    public long countByUserId(int userId) {
        return em.createQuery("SELECT COUNT(o) FROM Order o WHERE o.user.id = :uid", Long.class)
                .setParameter("uid", userId)
                .getSingleResult();
    }

    @Transactional
    public Order save(Order order) {
        if (order.getId() <= 0) {
            em.persist(order);
            return order;
        }
        return em.merge(order);
    }

    @Transactional
    public void saveOrUpdate(Order order) {
        if (order.getId() <= 0) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        Order o = em.find(Order.class, id);
        if (o == null) {
            return false;
        }
        em.remove(o);
        return true;
    }

    @Transactional
    public void delete(int id) {
        Order o = em.find(Order.class, id);
        if (o != null) {
            em.remove(o);
        }
    }

    @Transactional
    public void saveOrderItem(OrderItem item) {
        if (item.getId() <= 0) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }
}
