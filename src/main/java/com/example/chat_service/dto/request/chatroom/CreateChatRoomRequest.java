package com.example.chat_service.dto.request.chatroom;

import com.example.chat_service.domain.RoomStatus;
import com.example.chat_service.domain.RoomType;
import com.example.chat_service.domain.Visibility;
import com.example.chat_service.validator.common.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomRequest {

    @NotBlank
    @Size(max = 20)
    private String title;

    @Size(max = 30)
    private String description;

    private String avatarUrl;

    @NotBlank
    @ValidEnum(enumClass = RoomStatus.class, ignoreCase = true)
    @Schema(allowableValues = {"ACTIVE", "LEFT", "REMOVED"})
    private String status;

    @NotNull
    @ValidEnum(enumClass = RoomType.class, ignoreCase = true)
    @Schema(allowableValues = {"CHANNEL", "PERSONAL"})
    private String type;

    @NotNull
    @ValidEnum(enumClass = Visibility.class, ignoreCase = true)
    @Schema(allowableValues = {"PUBLIC", "PRIVATE"})
    private String visibility;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer memberLimit;
}
