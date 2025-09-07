package com.example.chat_service.domain;

import lombok.Getter;

@Getter
public enum RoomStatus {

    ACTIVE("활성"),
    ARCHIVED("아카이브"),
    DELETED("삭제됨");

    RoomStatus(String description) {
        this.description = description;
    }

    private final String description;

    public static RoomStatus from(String value) {
        return RoomStatus.valueOf(value.toUpperCase());
    }
}
