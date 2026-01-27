package com.stepbase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", ""})
    public String index() {
        // redirect to admin dashboard as default
        return "redirect:/admin/";
    }
}
