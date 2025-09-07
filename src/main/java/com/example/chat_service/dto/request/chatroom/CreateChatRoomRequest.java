package com.example.chat_service.dto.request.chatroom;

import com.example.chat_service.domain.JoinPolicy;
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

    @NotBlank(message = "title은 필수 값입니다")
    @Size(max = 20, message = "title은 20글자 이하이어야 합니다")
    private String title;

    @Size(max = 30)
    private String description;

    private String avatarUrl;

    @NotBlank
    @ValidEnum(enumClass = RoomStatus.class)
    @Schema(allowableValues = {"ACTIVE", "LEFT", "REMOVED"})
    private String status;

    @NotNull
    @ValidEnum(enumClass = RoomType.class)
    @Schema(allowableValues = {"CHANNEL", "PERSONAL"})
    private String type;

    @NotNull
    @ValidEnum(enumClass = Visibility.class)
    @Schema(allowableValues = {"PUBLIC", "PRIVATE"})
    private String visibility;

    @NotNull
    @ValidEnum(enumClass = JoinPolicy.class)
    @Schema(allowableValues = {"OPEN", "INVITE_ONLY"})
    private String policy;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer memberLimit;
}
