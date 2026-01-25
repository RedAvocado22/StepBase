package com.stepbase.product.repository;

import com.stepbase.product.Product;

import java.util.List;

public interface ProductRepository {
    /**
     * Lấy toàn bộ danh sách sản phẩm
     */
    List<Product> findAll();

    /**
     * Tìm sản phẩm theo khóa chính ID
     */
    Product findById(int id);

    /**
     * Lưu mới (persist) hoặc cập nhật (merge) sản phẩm
     */
    void saveOrUpdate(Product product);

    /**
     * Xóa sản phẩm khỏi database theo ID
     */
    void delete(int id);

    /**
     * Tìm kiếm sản phẩm theo mã thương hiệu
     */
    List<Product> findByBrandId(int brandId);

    /**
     * Tìm kiếm sản phẩm theo mã danh mục
     */
    List<Product> findByCategoryId(int categoryId);

    /**
     * Tìm kiếm sản phẩm theo tên (hỗ trợ tìm kiếm gần đúng)
     */
    List<Product> findByName(String name);

    /**
     * Lấy chi tiết sản phẩm và các biến thể (size, số lượng) đi kèm
     */
    Product findProductWithVariants(int id);
}
