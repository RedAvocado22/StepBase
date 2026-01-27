package com.stepbase.cart;

import com.stepbase.product.Product;
import com.stepbase.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    @Autowired
    public  CartController(ProductService productService) {
        this.productService = productService;
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("CART", cart);
        }
        return cart;
    }

    // =====================
    // VIEW CART
    // =====================
    @GetMapping
    public String viewCart(HttpSession session,
                           Model model,
                           @RequestParam(name = "page", defaultValue = "1") int page) {
        int size=2;
        Cart cart = getCart(session);
        List<CartItem> allItems=cart.getCartItems().stream().toList();
        int totalItems=allItems.size();

        int totalPages = (int) Math.ceil((double) totalItems / size);

        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<CartItem> pagedItems = Collections.emptyList();
        if (start < totalItems) {
            pagedItems = allItems.subList(start, end);
        }
        model.addAttribute("cartItems", pagedItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    // =====================
    // ADD TO CART
    // =====================
    @PostMapping("/add")
    public String addToCart(
            @RequestParam("productId") int productId,
            @RequestParam(value = "quantity",defaultValue = "1") int quantity,
            HttpSession session
    ) {
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/product?error=notfound";
        }else {
            getCart(session).addItem(product, quantity);
            return "redirect:/cart";
        }
    }

    // =====================
    // UPDATE QUANTITY
    // =====================
    @PostMapping("/update")
    public String updateCart(
            @RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity,
            HttpSession session
    ) {
        getCart(session).updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // =====================
    // REMOVE ITEM
    // =====================
    @PostMapping("/remove")
    public String removeItem(
            @RequestParam("productId") int productId,
            HttpSession session
    ) {
        getCart(session).removeItem(productId);
        return "redirect:/cart";
    }

    // =====================
    // CLEAR CART
    // =====================
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        getCart(session).clear();
        return "redirect:/cart";
    }

    @GetMapping("/errors")
    public String errorPage(@RequestParam("error") boolean error) {
        if(error) return "cart/error";
        else return "redirect:/cart";
    }
}
