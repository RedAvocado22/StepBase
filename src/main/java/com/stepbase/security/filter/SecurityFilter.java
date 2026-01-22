package com.stepbase.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// Security filter - validates auth tokens on all requests
@Component
public class SecurityFilter implements Filter {

    // Public paths - no auth required
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/public",
            "/api/auth/login",
            "/api/auth/register",
            "/error",
            "/actuator",
            "/swagger-ui",
            "/v3/api-docs"
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

        // Check auth header
        String authHeader = httpRequest.getHeader("Authorization");
        if (!isValidAuth(authHeader)) {
            sendUnauthorizedResponse(httpResponse, "Missing or invalid authentication token");
            return;
        }

        // Validate token
        String token = extractToken(authHeader);
        if (!validateToken(token)) {
            sendUnauthorizedResponse(httpResponse, "Invalid or expired token");
            return;
        }

        // TODO: Add user context if needed
        // request.setAttribute("userId", getUserIdFromToken(token));
        // request.setAttribute("userRole", getUserRoleFromToken(token));
        chain.doFilter(request, response);
    }

    // Check if path is public
    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    // Validate auth header format
    private boolean isValidAuth(String authHeader) {
        return authHeader != null &&
               (authHeader.startsWith("Bearer ") || authHeader.startsWith("Token "));
    }

    // Extract token from "Bearer <token>" or "Token <token>"
    private String extractToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else if (authHeader.startsWith("Token ")) {
            return authHeader.substring(6);
        }
        return authHeader;
    }

    // TODO: Implement token validation (JWT, DB lookup, etc.)
    private boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // TODO: Add actual validation logic
        // return jwtService.validateToken(token);
        return true;
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
