package com.epam.training.shipping.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {

    private final String jwtToken;
    private final JwsClaims jwsClaims;
    private final List<GrantedAuthority> authorities;

    public JwtUserDetails(String jwtToken, JwsClaims jwsClaims) {
        this.jwtToken = jwtToken;
        this.jwsClaims = jwsClaims;
        this.authorities = createAuthorities();
    }

    public String getJwtToken() {
        return jwtToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public List<GrantedAuthority> createAuthorities() {
        return jwsClaims.extractRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return jwsClaims.extractUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
