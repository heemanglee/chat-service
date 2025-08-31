package com.example.chat_service.service;

import static com.example.chat_service.entity.User.createUser;

import com.example.chat_service.dto.response.token.TokenPair;
import com.example.chat_service.dto.response.user.GetUserInfoResponse;
import com.example.chat_service.dto.response.user.UserLoginResponse;
import com.example.chat_service.dto.response.user.UserRegisterResponse;
import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.security.dto.CustomUserDetails;
import com.example.chat_service.security.jwt.JwtTokenProvider;
import com.example.chat_service.utils.PasswordUtils;
import com.example.chat_service.validator.UserValidator;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordUtils passwordUtils;
    private final TokenService tokenService;
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

    @Transactional
    public UserLoginResponse login(String email, String rawPassword) {
        /**
         * 사용자의 로그인을 처리합니다.
         */

        User findUser = userValidator.validateUserExistsForLogin(email);
        boolean isLoginSuccess = passwordUtils.matches(rawPassword, findUser.getPassword());

        // 로그인 성공 시 Access Token을 발급합니다.
        if (isLoginSuccess) {
            TokenPair tokenPair = tokenService.generateTokenPair(findUser);
            tokenService.saveRefreshToken(findUser.getEmail(), tokenPair.getRefreshToken());

            return new UserLoginResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
        }

        throw new IllegalArgumentException("Invalid email or password");
    }

    public GetUserInfoResponse getUserInfo(CustomUserDetails userDetails) {
        // SecurityContext에서 인증된 사용자의 정보 가져오기
        String email = userDetails.getEmail();
        String username = userDetails.getUsername();
        return GetUserInfoResponse.create(email, username);
    }

}
