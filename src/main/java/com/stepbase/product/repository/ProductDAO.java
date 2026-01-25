package com.stepbase.product.repository;

import com.stepbase.product.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProductDAO {
    private static final EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("JPAs");

    public ProductDAO() {
    }
    /**
     * Lấy danh sách tất cả sản phẩm
     */
    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            // Sử dụng Native Query như ví dụ của bạn
            String sql = "SELECT * FROM products";
            return em.createNativeQuery(sql, Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm sản phẩm theo ID
     */
    public Product findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Lưu mới hoặc cập nhật sản phẩm
     */
    public void saveOrUpdate(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            // Nếu id = 0 hoặc null (đối với Integer) thì persist, ngược lại merge
            if (product.getId() == 0) {
                em.persist(product);
            } else {
                em.merge(product);
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
     * Xóa sản phẩm theo ID
     */
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
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
     * Tìm sản phẩm theo Brand ID sử dụng Native Query
     */
    public List<Product> findByBrandId(int brandId) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "SELECT * FROM products WHERE brand_id = :brandId";
            return em.createNativeQuery(sql, Product.class)
                    .setParameter("brandId", brandId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm sản phẩm theo Category ID sử dụng Native Query
     */
    public List<Product> findByCategoryId(int categoryId) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "SELECT * FROM products WHERE category_id = :categoryId";
            return em.createNativeQuery(sql, Product.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Product> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "SELECT * FROM products WHERE name LIKE :name";
            return em.createNativeQuery(sql, Product.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Product findProductWithVariants(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            // Sử dụng JPQL với JOIN FETCH để lấy luôn danh sách Variants, tránh Lazy loading lỗi
            String jpql = "SELECT p FROM products p LEFT JOIN FETCH p.variants WHERE p.id = :id";
            return em.createQuery(jpql, Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
