package com.example.chat_service.service;

import static com.example.chat_service.entity.User.*;

import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.utils.PasswordUtils;
import com.example.chat_service.validator.UserValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordUtils passwordUtils;

    @Transactional
    public UserRegisterResponse register(String username, String email, String rawPassword) {
        /**
         * 사용자 회원가입을 처리합니다.
         */

        userValidator.validateForRegisterByEmail(email);

        User user = createUser(username, email, passwordUtils.encode(rawPassword));
        User savedUser = userRepository.save(user);

        return new UserRegisterResponse(savedUser);
    }

    public boolean login(String email, String rawPassword) {
        /**
         * 사용자의 로그인을 처리합니다.
         */

        User findUser = userValidator.validateUserExistsForLogin(email);
        return passwordUtils.matches(rawPassword, findUser.getPassword());
    }

}
