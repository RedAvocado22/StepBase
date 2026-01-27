Create database ecommerce;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS ecommerce.users (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    fullname     VARCHAR(255)        NOT NULL,
    email        VARCHAR(191) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    phone_number VARCHAR(20),
    gender       TINYINT    DEFAULT 0,
    is_active    TINYINT(1) DEFAULT 1,
    is_admin     TINYINT(1) DEFAULT 0,
    reset_code   VARCHAR(50),
    created_at   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    is_deleted   TINYINT(1) DEFAULT 0
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.brands (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL,
    image_url VARCHAR(255),
    is_deleted TINYINT(1) DEFAULT 0
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.categories (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL,
    image_url VARCHAR(255),
    is_deleted TINYINT(1) DEFAULT 0
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.colors (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(50) NOT NULL,
    hex_code VARCHAR(7),
    is_deleted TINYINT(1) DEFAULT 0
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.sizes (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     size_eu VARCHAR(10) NOT NULL,
    is_deleted TINYINT(1) DEFAULT 0
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.products (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    base_price DECIMAL(15, 2) NOT NULL,
    brand_id INT,
    category_id INT,
    color_id INT,
    INDEX (name),
    CONSTRAINT fk_prod_brand FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL,
    CONSTRAINT fk_prod_cat FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    CONSTRAINT fk_prod_color FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE SET NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.product_variants (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                product_id INT NOT NULL,
                                                size_id INT NOT NULL,
                                                stock_quantity INT NOT NULL DEFAULT 0,
                                                CONSTRAINT fk_var_prod FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_var_size FOREIGN KEY (size_id) REFERENCES sizes(id) ON DELETE CASCADE,
    UNIQUE KEY uq_prod_size (product_id, size_id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.orders (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      user_id INT NOT NULL,
                                      consignee_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    shipping_address TEXT NOT NULL,
    order_status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(15, 2) NOT NULL,
    is_paid TINYINT(1) DEFAULT 0,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ecommerce.order_items (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           order_id INT NOT NULL,
                                           variant_id INT NOT NULL,
                                           quantity INT NOT NULL,
                                           price_at_purchase DECIMAL(15, 2) NOT NULL,
    CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_variant FOREIGN KEY (variant_id) REFERENCES product_variants(id)
    ) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;