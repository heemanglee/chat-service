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
    public ResponseEntity<String> register(String userId, String username, String email, String rawPassword) {
        User user = User.createUser(userId, username, email, rawPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Registered Successfully");
    }

}
