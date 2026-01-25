package com.stepbase.cart;

import com.stepbase.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Cart {
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {
        int productId = product.getId();

        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            items.put(productId, item);
        }
    }

    public void updateQuantity(int productId, int quantity) {
        if (items.containsKey(productId)) {
            if (quantity <= 0) {
                items.remove(productId);
            } else {
                items.get(productId).setQuantity(quantity);
            }
        }
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public Collection<CartItem> getCartItems() {
        return items.values();
    }

    public BigDecimal getTotalAmount() {
        return items.values().stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
