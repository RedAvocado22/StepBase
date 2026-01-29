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

}


