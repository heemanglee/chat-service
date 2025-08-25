package com.example.chat_service.service;

import com.example.chat_service.dto.response.token.TokenPair;
import com.example.chat_service.entity.User;
import com.example.chat_service.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.ttl.refresh-token}")
    private long refreshTokenTtl;

    public TokenPair generateTokenPair(User user) {
        String accessToken = getGenerateAccessToken(user);
        String refreshToken = getGenerateRefreshToken(user);

        return TokenPair.create(accessToken, refreshToken);
    }

    public void saveRefreshToken(String email, String refreshToken) {
        refreshTokenService.save(email, refreshToken, refreshTokenTtl);
    }

    private String getGenerateAccessToken(User user) {
        return jwtTokenProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }

    private String getGenerateRefreshToken(User user) {
        return jwtTokenProvider.generateRefreshToken(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }
}
