package com.example.chat_service.service;

import static java.util.concurrent.TimeUnit.SECONDS;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REFRESH_TOKEN_PREFIX = "user:refresh_token:";

    public void save(String email, String refreshToken, long expirationSeconds) {
        /**
         * Redis에 Refresh Token을 저장합니다.
         * @param email 사용자 이메일
         * @param refreshToken RefreshToken
         * @param expirationSeconds 만료 시간
         */

        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.opsForValue().set(key, refreshToken, expirationSeconds, SECONDS);
    }

    public String getRefreshToken(String email) {
        /**
         * Redis에 저장된 Refresh Token을 반환합니다.
         * @param email 사용자 이메일
         */

        String key = REFRESH_TOKEN_PREFIX + email;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String email) {
        /**
         * Redis에 저장된 Refresh Token을 삭제합니다.
         * @param email 사용자 이메일
         */

        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.delete(key);
    }


}
