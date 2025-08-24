package com.example.chat_service.dto.response.user;

import com.example.chat_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegisterResponse {

    private Long userId;
    private String username;
    private String email;

    public UserRegisterResponse(User user) {
        this(user.getId(), user.getUsername(), user.getEmail());
    }
}
