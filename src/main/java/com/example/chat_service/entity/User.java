package com.example.chat_service.entity;

import static com.example.chat_service.constant.UserStatus.*;

import com.example.chat_service.constant.UserStatus;
import com.example.chat_service.converter.UserStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, length = 50)
    private String userId;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus status = OFFLINE;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    public User(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User createUser(String userId, String username, String email, String rawPassword) {
        return new User(userId, username, email, rawPassword);
    }

}
