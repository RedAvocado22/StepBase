package com.stepbase.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// Security filter - validates session-based auth on all requests
@Component
public class SecurityFilter implements Filter {

    // Public paths - no auth required
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/public",
            "/api/auth/login",
            "/api/auth/register",
            "/login",
            "/register",
            "/forgot-password",
            "/reset-password",
            "/logout",
            "/error",
            "/actuator",
            "/swagger-ui",
            "/v3/api-docs",
            "/static",
            "/css",
            "/js"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // Skip CORS preflight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        // Skip public paths
        if (isExcludedPath(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Check session-based authentication
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Redirect to login for web pages, send 401 for API calls
            if (requestPath.startsWith("/api/")) {
                sendUnauthorizedResponse(httpResponse, "Authentication required");
            } else {
                httpResponse.sendRedirect("/login");
            }
            return;
        }

        // User is authenticated, continue
        chain.doFilter(request, response);
    }

    // Check if path is public
    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    // Return 401 with error message
    private void sendUnauthorizedResponse(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = String.format(
            "{\"error\": \"Unauthorized\", \"message\": \"%s\"}",
            message
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
