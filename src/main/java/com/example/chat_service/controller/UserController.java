package com.example.chat_service.controller;

import com.example.chat_service.dto.request.user.UserRegisterRequest;
import com.example.chat_service.dto.response.CommonApiResponse;
import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users/register")
    public CommonApiResponse<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = userService.register(
                request.getUsername(),
                request.getEmail(),
                request.getRawPassword()
        );

        return CommonApiResponse.success(HttpStatus.CREATED, response);
    }

}
