package com.example.chat_service.dto.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Valid
@Getter
public class TokenRefreshRequest {

    @NotBlank(message = "email은 필수 값입니다")
    private String email;

    @NotBlank(message = "refreshToken은 필수 값입니다")
    private String refreshToken;
}
