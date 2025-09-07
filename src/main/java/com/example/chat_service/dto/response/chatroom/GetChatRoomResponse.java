package com.example.chat_service.dto.response.chatroom;

import com.example.chat_service.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetChatRoomResponse {

    private Long id;
    private String title;
    private String description;
    private String avatarUrl;
    private String chatRoomStatus;
    private String type;
    private String visibility;
    private String policy;
    private int memberLimit;

    public static GetChatRoomResponse of(ChatRoom chatRoom) {
        return new GetChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getTitle(),
                chatRoom.getDescription(),
                chatRoom.getAvatarUrl(),
                chatRoom.getStatus().name(),
                chatRoom.getType().name(),
                chatRoom.getVisibility().name(),
                chatRoom.getPolicy().name(),
                chatRoom.getMemberLimit()
        );
    }
}
