package com.example.chat_service.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    
    ONLINE("ONLINE", "온라인"),
    OFFLINE("OFFLINE", "오프라인");

    private final String status;
    private final String description;
    
}
