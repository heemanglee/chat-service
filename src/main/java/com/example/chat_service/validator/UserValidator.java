package com.example.chat_service.validator;

import com.example.chat_service.entity.User;
import com.example.chat_service.exception.InvalidTokenException;
import com.example.chat_service.repository.UserRepository;
import com.example.chat_service.service.RefreshTokenService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public void validateForRegisterByEmail(String email) {
        validateEmailDuplicate(email);
    }

    public void validateRefreshToken(String email, String refreshToken) {
        // Redis에 저장된 Refresh Token 조회
        String findRefreshToken = refreshTokenService.getRefreshToken(email);
        if (findRefreshToken == null) {
            throw new NoSuchElementException("Not Found Refresh Token with email: " + email);
        }

        // 조회한 토큰과 클라이언트에서 전달받은 토큰이 일치하지 않으면 예외 발생
        if (!findRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }
    }

    private void validateEmailDuplicate(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("Duplicate User Email: " + email);
        });
    }

    public User validateUserExistsForLogin(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Not Found User by Email: " + email));
    }

}
