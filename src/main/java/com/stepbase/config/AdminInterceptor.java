package com.stepbase.config;

import com.stepbase.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(request.getContextPath() + "/admin")) {
            User currentUser = (User) request.getSession().getAttribute("currentUser");

            if (currentUser == null || (!"ADMIN".equals(currentUser.getRole()) && !"STAFF".equals(currentUser.getRole()))) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=Admin access required");
                return false;
            }
        }
        return true;
    }
}

