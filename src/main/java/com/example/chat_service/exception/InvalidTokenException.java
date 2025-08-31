package com.example.chat_service.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(String detail) {
        super(detail);
    }

    public InvalidTokenException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
