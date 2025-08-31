package com.example.chat_service.controller;

import com.example.chat_service.dto.request.user.TokenRefreshRequest;
import com.example.chat_service.dto.request.user.UserLoginRequest;
import com.example.chat_service.dto.request.user.UserRegisterRequest;
import com.example.chat_service.dto.response.CommonApiResponse;
import com.example.chat_service.dto.response.token.TokenPair;
import com.example.chat_service.dto.response.user.TokenRefreshResponse;
import com.example.chat_service.dto.response.user.UserLoginResponse;
import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.service.RefreshTokenService;
import com.example.chat_service.service.TokenService;
import com.example.chat_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public CommonApiResponse<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = userService.register(
                request.getUsername(),
                request.getEmail(),
                request.getRawPassword()
        );

        return CommonApiResponse.success(HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public CommonApiResponse<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.login(request.getEmail(), request.getRawPassword());
        return CommonApiResponse.success(response);
    }

    @PostMapping("/refresh")
    public CommonApiResponse<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        TokenPair newToken = tokenService.refreshToken(request.getEmail(), request.getRefreshToken());
        TokenRefreshResponse response = TokenRefreshResponse.create(newToken);
        return CommonApiResponse.success(HttpStatus.CREATED, response);
    }

}
