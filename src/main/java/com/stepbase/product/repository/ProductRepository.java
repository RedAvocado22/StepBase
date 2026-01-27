package com.stepbase.product.repository;

import com.stepbase.product.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Product> findById(int id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    public List<Product> findAll(int offset, int limit) {
        return em.createQuery("SELECT p FROM Product p ORDER BY p.id DESC", Product.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p ORDER BY p.id DESC", Product.class)
                .getResultList();
    }

    public List<Product> searchByName(String keyword, int offset, int limit) {
        String like = keyword == null ? "%" : ("%" + keyword + "%");
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :kw ORDER BY p.id DESC", Product.class)
                .setParameter("kw", like)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Product> findByName(String name) {
        String like = name == null ? "%" : ("%" + name + "%");
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :kw ORDER BY p.id DESC", Product.class)
                .setParameter("kw", like)
                .getResultList();
    }

    public List<Product> findByBrandId(int brandId) {
        return em.createQuery("SELECT p FROM Product p WHERE p.brand.id = :bid ORDER BY p.id DESC", Product.class)
                .setParameter("bid", brandId)
                .getResultList();
    }

    public List<Product> findByCategoryId(int categoryId) {
        return em.createQuery("SELECT p FROM Product p WHERE p.category.id = :cid ORDER BY p.id DESC", Product.class)
                .setParameter("cid", categoryId)
                .getResultList();
    }

    public Product findProductWithVariants(int id) {
        Product product = em.find(Product.class, id);
        if (product != null && product.getVariants() != null) {
            product.getVariants().size(); // Force lazy loading
        }
        return product;
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
    }

    @Transactional
    public Product save(Product product) {
        if (product.getId() <= 0) {
            em.persist(product);
            return product;
        }
        return em.merge(product);
    }

    @Transactional
    public void saveOrUpdate(Product product) {
        if (product.getId() <= 0) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        Product p = em.find(Product.class, id);
        if (p == null) {
            return false;
        }
        em.remove(p);
        return true;
    }

    @Transactional
    public void delete(int id) {
        Product p = em.find(Product.class, id);
        if (p != null) {
            em.remove(p);
        }
    }
}
