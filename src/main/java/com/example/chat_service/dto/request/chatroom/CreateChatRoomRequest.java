package com.example.chat_service.dto.request.chatroom;

import com.example.chat_service.domain.RoomStatus;
import com.example.chat_service.domain.RoomType;
import com.example.chat_service.domain.Visibility;
import com.example.chat_service.validator.common.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CreateChatRoomRequest {

    @Size(max = 20)
    private String title;

    @Size(max = 30)
    private String description;

    private String avatarUrl;

    @NotBlank
    @ValidEnum(enumClass = RoomStatus.class, ignoreCase = true)
    private String status;

    @NotNull
    @ValidEnum(enumClass = RoomType.class, ignoreCase = true)
    private String type;

    @NotNull
    @ValidEnum(enumClass = Visibility.class, ignoreCase = true)
    private String visibility;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer memberLimit;
}
