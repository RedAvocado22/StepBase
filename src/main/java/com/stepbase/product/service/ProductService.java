package com.stepbase.product.service;

import com.stepbase.product.Product;
import com.stepbase.product.repository.ProductDAO;
import com.stepbase.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService implements ProductRepository {
    @Autowired
    private ProductDAO productDAO;
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(int id) {
        return productDAO.findById(id);
    }

    @Override
    public void saveOrUpdate(Product product) {
        productDAO.saveOrUpdate(product);
    }

    @Override
    public void delete(int id) {
        productDAO.delete(id);
    }

    @Override
    public List<Product> findByBrandId(int brandId) {
        return productDAO.findByBrandId(brandId);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findByName(String name) {
        return productDAO.findByName(name);
    }

    @Override
    public Product findProductWithVariants(int id) {
        return productDAO.findProductWithVariants(id);
    }
}
