package com.epam.training.order.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static JwtUserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtUserDetails) authentication.getPrincipal();
    }

    public static String getAuthenticatedUsername() {
        return getAuthenticatedUserDetails().getUsername();
    }
}
