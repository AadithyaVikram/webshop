package com.epam.training.order.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
public class JwsParser {

    @Value("${token.secret.publicKey}")
    String jwtPublicKey;

    public JwsClaims extractClaims(String token) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token);

        return new JwsClaims(claims);
    }

    private PublicKey getPublicKey() {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            byte[] keyBytes = Decoders.BASE64.decode(jwtPublicKey);
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(keyBytes);
            return factory.generatePublic(encodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
