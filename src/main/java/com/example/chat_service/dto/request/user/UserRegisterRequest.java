package com.example.chat_service.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
    /**
     * 사용자 회원가입 시 주입받는 값 검증 DTO
     */

    @NotBlank(message = "username은 필수 값입니다")
    private String username;

    @Email(message = "올바른 email 형태가 아닙니다")
    @NotBlank(message = "email은 필수 값입니다")
    private String email;

    @NotBlank(message = "rawPassword는 필수 값입니다")
    private String rawPassword;
}
