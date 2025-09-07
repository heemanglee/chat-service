package com.example.chat_service.dto.response.chatroom;

import com.example.chat_service.domain.ChatRoom;
import com.example.chat_service.domain.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateChatRoomResponse {

    private Long id;
    private String title;
    private String description;
    private String avatarUrl;
    private String chatRoomStatus;
    private String type;
    private String visibility;
    private String policy;
    private int memberLimit;
    private String role;
    private String memberStatus;
    private Long senderId;
    private LocalDateTime joinedAt;
    private String roomSetting;

    public static CreateChatRoomResponse from(ChatRoom chatRoom, ChatRoomMember chatRoomMember) {
        return new CreateChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getTitle(),
                chatRoom.getDescription(),
                chatRoom.getAvatarUrl(),
                chatRoom.getStatus().name(),
                chatRoom.getType().name(),
                chatRoom.getVisibility().name(),
                chatRoom.getPolicy().name(),
                chatRoom.getMemberLimit(),
                chatRoomMember.getRole().name(),
                chatRoomMember.getStatus().name(),
                chatRoomMember.getInvitedBy().getId(),
                chatRoomMember.getJoinedAt(),
                chatRoomMember.getSetting().name()
        );
    }

}
