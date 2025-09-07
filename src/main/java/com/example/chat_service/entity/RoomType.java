package com.example.chat_service.entity;

import lombok.Getter;

@Getter
public enum RoomType {

    CHANNEL("채널"),
    PERSONAL("DM");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }
}
