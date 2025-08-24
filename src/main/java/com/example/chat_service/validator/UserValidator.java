package com.example.chat_service.validator;

import com.example.chat_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateForRegisterByEmail(String email) {
        validateEmailDuplicate(email);
    }

    private void validateEmailDuplicate(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("Duplicate User Email: " + email);
        });
    }

}
