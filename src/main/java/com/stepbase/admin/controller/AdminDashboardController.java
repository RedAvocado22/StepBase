package com.stepbase.admin.controller;

import com.stepbase.admin.service.AdminDashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getStats());
        model.addAttribute("recentOrders", dashboardService.getRecentOrders(8));
        model.addAttribute("lowStock", dashboardService.getLowStockVariants(8));
        return "admin/dashboard";
    }
}
