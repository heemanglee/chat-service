package com.example.chat_service.dto.response.user;

import com.example.chat_service.dto.response.token.TokenPair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenRefreshResponse {

    private final String accessToken;
    private final String refreshToken;

    public static TokenRefreshResponse create(TokenPair tokenPair) {
        return new TokenRefreshResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }
}
