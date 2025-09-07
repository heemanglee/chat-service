package com.example.chat_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import static com.example.chat_service.entity.MemberStatus.ACTIVE;
import static com.example.chat_service.entity.NotificationSetting.ON;

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

}
