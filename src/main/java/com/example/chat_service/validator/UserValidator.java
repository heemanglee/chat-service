package com.example.chat_service.validator;

import com.example.chat_service.entity.User;
import com.example.chat_service.repository.UserRepository;
import java.util.NoSuchElementException;
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

    public User validateUserExistsForLogin(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Not Found User by Email: " + email));
    }

}
