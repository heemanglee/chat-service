package com.example.chat_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.chat_service.domain.MemberRole.*;
import static com.example.chat_service.domain.MemberStatus.ACTIVE;
import static com.example.chat_service.domain.NotificationSetting.ON;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_room_member", uniqueConstraints = {@UniqueConstraint(columnNames = {"chat_room_id", "user_id"})})
public class ChatRoomMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status = ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by")
    private User invitedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();
    private LocalDateTime leftAt;

    private Long lastReadMessageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationSetting setting = ON;

    public static ChatRoomMember create(ChatRoom chatRoom, User sender, User receiver, LocalDateTime now) {
        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .user(receiver)
                .role(OWNER)
                .status(ACTIVE)
                .invitedBy(sender)
                .joinedAt(now)
                .setting(ON)
                .build();
    }

}
