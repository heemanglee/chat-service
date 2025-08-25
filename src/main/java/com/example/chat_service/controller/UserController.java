package com.example.chat_service.controller;

import com.example.chat_service.dto.request.user.UserLoginRequest;
import com.example.chat_service.dto.request.user.UserRegisterRequest;
import com.example.chat_service.dto.response.CommonApiResponse;
import com.example.chat_service.dto.response.user.UserLoginResponse;
import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}
