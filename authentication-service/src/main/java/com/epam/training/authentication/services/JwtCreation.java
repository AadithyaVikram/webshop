package com.epam.training.authentication.services;

import com.epam.training.authentication.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtCreation {

    @Value("${token.secret.privateKey}")
    private String jwtPrivateKey;

    @Value("${token.expirationms}")
    private Long jwtExpirationMs;

    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", getRoles(user));
        extraClaims.put("name", user.getName());
        return generateToken(user.getEmail(), extraClaims);
    }

    private static String[] getRoles(User user) {
        String[] roles = new String[1];
        roles[0] = user.getRole().toString();
        return roles;
    }

    private String generateToken(String subject, Map<String, Object> extraClaims) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            byte[] keyBytes = Decoders.BASE64.decode(jwtPrivateKey);
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            return factory.generatePrivate(encodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
