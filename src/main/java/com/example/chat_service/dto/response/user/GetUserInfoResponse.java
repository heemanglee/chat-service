package com.example.chat_service.dto.response.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GetUserInfoResponse {

    private final String email;

    public static GetUserInfoResponse create(String email) {
        return new GetUserInfoResponse(email);
    }
}
