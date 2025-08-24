package com.example.chat_service.service;

import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Transactional
    public ResponseEntity<String> register(String username, String email, String rawPassword) {
        /**
         * 사용자 회원가입을 처리합니다.
         */

        userValidator.validateForRegisterByEmail(email);

        User user = User.createUser(username, email, rawPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Registered Successfully");
    }

}
