package com.example.chat_service.entity;

public enum RoomStatus {

    ACTIVE("활성"),
    ARCHIVED("아카이브"),
    DELETED("삭제됨");

    RoomStatus(String status) {
        this.status = status;
    }

    private final String status;
}
