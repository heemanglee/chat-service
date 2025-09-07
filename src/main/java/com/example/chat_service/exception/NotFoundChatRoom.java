package com.example.chat_service.exception;

public class NotFoundChatRoom extends RuntimeException {
    public NotFoundChatRoom(String message) {
        super(message);
    }
}
