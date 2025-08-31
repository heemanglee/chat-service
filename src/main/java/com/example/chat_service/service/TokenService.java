package com.example.chat_service.service;

import com.example.chat_service.dto.response.token.TokenPair;
import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.security.jwt.JwtTokenProvider;
import com.example.chat_service.validator.UserValidator;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Value("${jwt.ttl.refresh-token}")
    private long refreshTokenTtl;

    public TokenPair generateTokenPair(User user) {
        String accessToken = getGenerateAccessToken(user);
        String refreshToken = getGenerateRefreshToken(user);

        return TokenPair.create(accessToken, refreshToken);
    }

    public void saveRefreshToken(String email, String refreshToken) {
        System.out.println("refrestToken: " + refreshToken);
        refreshTokenService.save(email, refreshToken, refreshTokenTtl);
    }

    public TokenPair refreshToken(String email, String refreshToken) {
        // 사용자 조회
        User findUser = userValidator.validateUserExistsForLogin(email);

        // Refresh Token 검증
        userValidator.validateRefreshToken(findUser.getEmail(), refreshToken);

        // 새로운 토큰 발급
        TokenPair newToken = generateTokenPair(findUser);
        refreshTokenService.save(email, newToken.getRefreshToken(), refreshTokenTtl);
        return newToken;
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
