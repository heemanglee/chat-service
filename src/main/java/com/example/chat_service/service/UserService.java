package com.example.chat_service.service;

import static com.example.chat_service.entity.User.*;

import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserRegisterResponse register(String username, String email, String rawPassword) {
        /**
         * 사용자 회원가입을 처리합니다.
         */

        userValidator.validateForRegisterByEmail(email);

        User user = createUser(username, email, passwordEncoder.encode(rawPassword));
        User savedUser = userRepository.save(user);

        return new UserRegisterResponse(savedUser);
    }

}
