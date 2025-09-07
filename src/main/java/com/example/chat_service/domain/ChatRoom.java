package com.example.chat_service.domain;

import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String title;

    @Size(max = 30)
    private String description;
    private String avatarUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JoinPolicy policy;

    @Min(1)
    @Max(100)
    private Integer memberLimit;

    public static ChatRoom create(CreateChatRoomRequest request) {
        return ChatRoom.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .avatarUrl(request.getAvatarUrl())
                .status(RoomStatus.valueOf(request.getStatus()))
                .type(RoomType.valueOf(request.getType()))
                .visibility(Visibility.valueOf(request.getVisibility()))
                .policy(JoinPolicy.valueOf(request.getPolicy()))
                .memberLimit(request.getMemberLimit())
                .build();
    }
}
