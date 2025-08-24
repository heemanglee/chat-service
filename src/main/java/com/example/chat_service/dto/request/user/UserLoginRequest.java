package com.example.chat_service.dto.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Valid
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "email은 필수 값입니다")
    private String email;

    @NotBlank(message = "rawPassword는 필수 값입니다")
    private String rawPassword;

}
