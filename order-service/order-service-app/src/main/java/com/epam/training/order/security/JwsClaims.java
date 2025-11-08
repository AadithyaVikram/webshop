package com.epam.training.order.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Date;
import java.util.List;

public final class JwsClaims {
    private final Jws<Claims> claims;

    public JwsClaims(Jws<Claims> claims) {
        this.claims = claims;
    }

    public boolean isTokenValid() {
        return !isTokenExpired();
    }

    public boolean isTokenExpired() {
        return extractExpiration().before(new Date());
    }

    public  Date extractExpiration() {
        return claims.getPayload().getExpiration();
    }

    public  String extractUserName() {
        return claims.getPayload().getSubject();
    }

    public  List<String> extractRoles() {
        return claims.getPayload().get("roles", List.class);
    }
}
