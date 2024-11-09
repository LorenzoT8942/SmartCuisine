package com.authentication.authenticationManagement.service;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {
    @Value("${authentication.service.jwtSecret}")
    private String secretKey;
    @Value("${authentication.service.jwtExpirationTime}")
    private long expirationTime;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        System.out.println(secretKey);
        byte[] decodeKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public boolean validateToken(String token) {
        try {
            boolean ret = Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody().getExpiration().after(Date.from(Instant.now()));
            return ret;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(generateKey()).build()
            .parseClaimsJws(token)
            .getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
