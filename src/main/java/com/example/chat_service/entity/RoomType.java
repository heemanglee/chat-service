package com.example.chat_service.entity;

public enum RoomType {

    CHANNEL("채널"),
    PERSONAL("DM");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }
}
