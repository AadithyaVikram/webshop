package com.epam.training.keygen;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

import java.security.KeyPair;

public class KeyGenerator {
     public static void generateAndPrintKeys() {
        KeyPair keyPair = Jwts.SIG.RS256.keyPair().build();

        System.out.println("Public: " + Encoders.BASE64.encode(keyPair.getPublic().getEncoded()));
        System.out.println("Private: " + Encoders.BASE64.encode(keyPair.getPrivate().getEncoded()));
    }

}
