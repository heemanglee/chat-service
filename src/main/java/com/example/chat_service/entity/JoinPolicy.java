package com.example.chat_service.entity;

import lombok.Getter;

@Getter
public enum JoinPolicy {

    OPEN("누구나 입장 가능"),
    INVITE_ONLY("초대받은 사용자만 입장 가능");

    private final String description;

    JoinPolicy(String description) {
        this.description = description;
    }
}
