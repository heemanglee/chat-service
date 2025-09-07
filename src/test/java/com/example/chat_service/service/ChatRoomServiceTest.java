package com.example.chat_service.service;

import com.example.chat_service.domain.User;
import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import com.example.chat_service.dto.response.chatroom.CreateChatRoomResponse;
import com.example.chat_service.dto.response.chatroom.GetChatRoomResponse;
import com.example.chat_service.exception.DuplicateTitleException;
import com.example.chat_service.exception.UnauthorizedAccessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ChatRoomServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ChatRoomService chatRoomService;

    @Test
    void title이_중복되지_않는_채팅방을_생성할_수_있다() {
        // 사용자 생성
        User user1 = User.createUser("test1", "test1@example.com", "test1");
        User user2 = User.createUser("test2", "test2@example.com", "test2");
        userService.register(user1.getUsername(), user1.getEmail(), user1.getPassword());
        userService.register(user2.getUsername(), user2.getEmail(), user2.getPassword());

        // 채팅방 생성
        String title1 = "title1";
        CreateChatRoomRequest request1 = CreateChatRoomRequest.builder()
                .title(title1)
                .status("ACTIVE")
                .type("CHANNEL")
                .visibility("PUBLIC")
                .policy("OPEN")
                .memberLimit(1)
                .build();
        CreateChatRoomResponse response1 = chatRoomService.createRoom(user1.getEmail(), request1);

        String title2 = "title2";
        CreateChatRoomRequest request2 = CreateChatRoomRequest.builder()
                .title(title2)
                .status("ACTIVE")
                .type("CHANNEL")
                .visibility("PUBLIC")
                .policy("OPEN")
                .memberLimit(1)
                .build();
        CreateChatRoomResponse response2 = chatRoomService.createRoom(user2.getEmail(), request2);

        // 검증
        assertThat(response1).isNotNull();
        assertThat(response1.getTitle()).isEqualTo(title1);

        assertThat(response2).isNotNull();
        assertThat(response2.getTitle()).isEqualTo(title2);

        assertThat(response1.getId()).isNotEqualTo(response2.getId());
    }

    @Test
    void title이_중복되면_DuplicateTitleException_예외가_발생한다() {
        // 사용자 생성
        User user1 = User.createUser("test1", "test1@example.com", "test1");
        User user2 = User.createUser("test2", "test2@example.com", "test2");
        userService.register(user1.getUsername(), user1.getEmail(), user1.getPassword());
        userService.register(user2.getUsername(), user2.getEmail(), user2.getPassword());

        // 채팅방 생성
        String title = "title";
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title(title)
                .status("ACTIVE")
                .type("CHANNEL")
                .visibility("PUBLIC")
                .policy("OPEN")
                .memberLimit(1)
                .build();
        CreateChatRoomResponse room1 = chatRoomService.createRoom(user1.getEmail(), request);

        // 검증
        assertThatThrownBy(() -> chatRoomService.createRoom(user2.getEmail(), request))
                .isInstanceOf(DuplicateTitleException.class)
                .hasMessageContaining("Duplicate Chat Room Title: " + title);
    }

    @Test
    void 생성한_채팅방을_조회할_수_있다() {
        // 사용자 생성
        User user1 = User.createUser("test1", "test1@example.com", "test1");
        userService.register(user1.getUsername(), user1.getEmail(), user1.getPassword());

        // 채팅방 생성 및 조회
        String title = "title";
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title(title)
                .status("ACTIVE")
                .type("CHANNEL")
                .visibility("PUBLIC")
                .policy("OPEN")
                .memberLimit(1)
                .build();
        CreateChatRoomResponse room1 = chatRoomService.createRoom(user1.getEmail(), request);

        GetChatRoomResponse findChatRoom = chatRoomService.findChatRoom(user1.getEmail(), room1.getId());

        // 검증
        assertThat(findChatRoom).isNotNull();
        assertThat(findChatRoom.getId()).isEqualTo(room1.getId());
        assertThat(findChatRoom.getTitle()).isEqualTo(room1.getTitle());
    }

    @Test
    void 참여하지_않는_채팅방_조회시_UnauthorizedAccessException_예외가_발생한다() {
        // 사용자 생성
        User user1 = User.createUser("test1", "test1@example.com", "test1");
        User user2 = User.createUser("test2", "test2@example.com", "test2");
        userService.register(user1.getUsername(), user1.getEmail(), user1.getPassword());
        userService.register(user2.getUsername(), user2.getEmail(), user2.getPassword());

        // 채팅방 생성
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title("title")
                .status("ACTIVE")
                .type("CHANNEL")
                .visibility("PUBLIC")
                .policy("OPEN")
                .memberLimit(1)
                .build();
        CreateChatRoomResponse room1 = chatRoomService.createRoom(user1.getEmail(), request);

        // 검증
        assertThatThrownBy(() -> chatRoomService.findChatRoom(user2.getEmail(), room1.getId()))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessageContaining("User is not a member of this chat room Id: " + room1.getId());
    }

}