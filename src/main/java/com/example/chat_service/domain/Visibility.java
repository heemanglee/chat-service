package com.example.chat_service.domain;

import lombok.Getter;

@Getter
public enum Visibility {

    PUBLIC("공개 채널"),
    PRIVATE("비공개 채널");

    private final String description;

    Visibility(String description) {
        this.description = description;
    }

    public static Visibility from(String value) {
        return Visibility.valueOf(value.toUpperCase());
    }
}
