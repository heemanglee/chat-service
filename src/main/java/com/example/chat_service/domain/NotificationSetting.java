package com.example.chat_service.domain;

import lombok.Getter;

@Getter
public enum NotificationSetting {

    ON,
    OFF;

    public static NotificationSetting from(String value) {
        return NotificationSetting.valueOf(value.toUpperCase());
    }
}
