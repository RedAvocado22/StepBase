package com.stepbase.cart;

import com.stepbase.product.Product;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartItem {
    private Product product;
    private int quantity;

    public BigDecimal getSubTotal() {
        return product.getBasePrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}
