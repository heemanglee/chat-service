package com.example.chat_service.service;

import static com.example.chat_service.entity.User.createUser;

import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.security.jwt.JwtTokenProvider;
import com.example.chat_service.utils.PasswordUtils;
import com.example.chat_service.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordUtils passwordUtils;
    private final JwtTokenProvider jwtTokenProvider;

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

    public String login(String email, String rawPassword) {
        /**
         * 사용자의 로그인을 처리합니다.
         */

        User findUser = userValidator.validateUserExistsForLogin(email);
        String accessToken = jwtTokenProvider.generateAccessToken(
                findUser.getId(),
                findUser.getEmail(),
                findUser.getUsername()
        );

        boolean isLoginSuccess = passwordUtils.matches(rawPassword, findUser.getPassword());

        // 로그인 성공 시 Access Token을 발급합니다.
        if (isLoginSuccess) {
            return jwtTokenProvider.generateAccessToken(
                    findUser.getId(),
                    findUser.getEmail(),
                    findUser.getUsername()
            );
        }

        throw new IllegalArgumentException("Invalid email or password");
    }

}
