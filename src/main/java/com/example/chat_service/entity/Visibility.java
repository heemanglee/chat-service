package com.example.chat_service.entity;

import lombok.Getter;

@Getter
public enum Visibility {

    PUBLIC("공개 채널"),
    PRIVATE("비공개 채널");

    private final String description;

    Visibility(String description) {
        this.description = description;
    }
}
