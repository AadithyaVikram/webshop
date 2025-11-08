package com.epam.training.order.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwsParser jwsParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            handleJwtToken(request, authHeader);
        }
        filterChain.doFilter(request, response);
    }

    private void handleJwtToken(HttpServletRequest request, String authHeader) {
        final String jwtToken = authHeader.substring(7);
        JwsClaims jwsClaims = null;
        try {
            jwsClaims = jwsParser.extractClaims(jwtToken);
        } catch (JwtException e) {
            log.error("JWT parse failed with error: {}, token: {}", e.getMessage(), jwtToken);
        }
        if (jwsClaims != null && jwsClaims.isTokenValid() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new JwtUserDetails(jwtToken, jwsClaims);
            log.debug("successfully resolved token, user: {}", userDetails.getUsername());
            createSecurityContext(request, userDetails);
        }
    }

    private static void createSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}