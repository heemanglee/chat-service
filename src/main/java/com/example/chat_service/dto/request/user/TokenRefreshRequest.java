package com.example.chat_service.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRefreshRequest {

    private String email;
    private String refreshToken;
}
