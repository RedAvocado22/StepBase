package com.stepbase.user.repository;

import com.stepbase.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findById(int id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByEmail(String email) {
        return em.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email",
                        User.class
                )
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    public Optional<User> findByResetCode(String resetCode) {
        return em.createQuery(
                        "SELECT u FROM User u WHERE u.resetCode = :resetCode",
                        User.class
                )
                .setParameter("resetCode", resetCode)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public User save(User user) {
        if (user.getId() <= 0) {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }

    public List<User> findAll(int offset, int limit) {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id DESC", User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
    }
}
