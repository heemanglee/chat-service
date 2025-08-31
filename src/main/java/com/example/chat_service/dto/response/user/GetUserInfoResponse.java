package com.example.chat_service.dto.response.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GetUserInfoResponse {

    private final String email;
    private final String username;

    public static GetUserInfoResponse create(String email, String username) {
        return new GetUserInfoResponse(email, username);
    }
}
