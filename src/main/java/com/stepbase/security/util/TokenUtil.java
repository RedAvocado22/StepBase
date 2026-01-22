package com.stepbase.security.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Token utility helpers
// TODO: Consider using JWT library (jjwt, java-jwt) for production
public class TokenUtil {

    // Decode base64 token
    public static String decodeBase64(String encoded) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encoded);
            return new String(decoded, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Encode to base64
    public static String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    // TODO: Check token expiration (JWT exp claim or DB lookup)
    public static boolean isTokenExpired(String token) {
        // return claims.getExpiration().before(new Date());
        return false;
    }

    // TODO: Extract userId from token (JWT sub/userId claim or DB lookup)
    public static String getUserIdFromToken(String token) {
        return null;
    }

    // TODO: Extract user role from token (JWT role claim or DB lookup)
    public static String getUserRoleFromToken(String token) {
        return null;
    }
}
