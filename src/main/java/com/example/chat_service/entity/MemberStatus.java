package com.example.chat_service.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {

    ACTIVE("참여 중인 상태"),
    LEFT("자발적으로 나간 상태"),
    REMOVED("관리자에 의해 나가진 상태");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }
}
