package com.example.chat_service.dto.response.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPair {

    private String accessToken;
    private String refreshToken;

    public static TokenPair create(String accessToken, String refreshToken) {
        return new TokenPair(accessToken, refreshToken);
    }
}
