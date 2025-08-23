package com.example.chat_service.converter;

import com.example.chat_service.constant.UserStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * UserStatus 객체와 DB의 String 컬럼을 변환하는 클래스
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        // ENUM 객체의 status 필드를 DB에 저장합니다.
        return attribute != null ? attribute.getStatus() : null;
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        for (UserStatus userStatus : UserStatus.values()) {
            // DB에서 읽은 status 값과 일치하는 경우 엔티티 객체에 매핑합니다.
            if (userStatus.getStatus().equals(dbData)) {
                return userStatus;
            }
        }

        throw new IllegalArgumentException("Unkown database values: " + dbData);
    }
}
