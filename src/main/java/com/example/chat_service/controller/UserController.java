package com.example.chat_service.controller;

import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/api/users/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String username = body.get("username");
        String email = body.get("email");
        String rawPassword = body.get("password");

        // 요청 body에 담긴 데이터가 null이 아님을 가정하고 진행.
        User user = new User(
                userId, username, email, rawPassword
        );

        userRepository.save(user);

        return ResponseEntity.ok(
                "Registered Successfully"
        );
    }
}
