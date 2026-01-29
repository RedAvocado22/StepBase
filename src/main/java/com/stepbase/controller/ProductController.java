package com.stepbase.controller;


import com.stepbase.model.Product;
import com.stepbase.model.User;
import com.stepbase.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "product-list";
        }
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/admin/add")
    public String showAddProduct(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            return "redirect:/auth/login?error=Admin access required";
        }
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/admin/add")
    public String addProduct(@ModelAttribute Product product, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            return "redirect:/auth/login?error=Admin access required";
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditProduct(@PathVariable("id") Long id, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            return "redirect:/auth/login?error=Admin access required";
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                               @ModelAttribute Product product,
                               HttpSession session,
                               Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
            return "redirect:/auth/login?error=Admin access required";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }
}


