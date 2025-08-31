package com.example.chat_service.security.jwt;

import static io.jsonwebtoken.SignatureAlgorithm.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;

    @Value("${jwt.ttl.access-token}")
    private long accessTokenTtl;

    @Value("${jwt.ttl.refresh-token}")
    private long refreshTokenTtl;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long userId, String email, String username) {
        /**
         * Access Token을 발급합니다.
         */

        long now = (new Date()).getTime();
        Date accessTokenExpired = new Date(now + accessTokenTtl);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("email", email);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setClaims(claims)
                .setExpiration(accessTokenExpired)
                .signWith(key, HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId, String email, String username) {
        /**
         * Refresh Token을 발급합니다.
         */

        long now = (new Date()).getTime();
        Date tokenExpired = new Date(now + refreshTokenTtl);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("username", username);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setClaims(claims)
                .setExpiration(tokenExpired)
                .signWith(key, HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        /**
         *
         */
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

}
