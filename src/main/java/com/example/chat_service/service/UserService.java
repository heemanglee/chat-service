package com.example.chat_service.service;

import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> register(String username, String email, String rawPassword) {
        /**
         * 사용자 회원가입을 처리합니다.
         */

        // 중복된 이메일 여부 검증
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("Duplicate Email by + " + email);
        });

        User user = User.createUser(username, email, rawPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Registered Successfully");
    }

}
