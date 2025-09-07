package com.example.chat_service.service;

import com.example.chat_service.domain.ChatRoom;
import com.example.chat_service.domain.ChatRoomMember;
import com.example.chat_service.domain.User;
import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import com.example.chat_service.dto.response.chatroom.CreateChatRoomResponse;
import com.example.chat_service.exception.DuplicateTitleException;
import com.example.chat_service.repository.ChatRoomMemberRepository;
import com.example.chat_service.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserService userService;

    @Transactional
    public CreateChatRoomResponse createRoom(String email, CreateChatRoomRequest request) {
        User findUser = userService.findByEmail(email);
        boolean existChatRoom = chatRoomRepository.existsByTitle(request.getTitle());

        // 채팅방의 title은 고유한 값이다.
        if (existChatRoom) {
            throw new DuplicateTitleException("Duplicate Chat Room Title: " + request.getTitle());
        }

        // ChatRoom 생성 및 반환
        ChatRoom createChatRoom = ChatRoom.create(request);
        ChatRoom savedChatRoom = chatRoomRepository.save(createChatRoom);

        LocalDateTime now = LocalDateTime.now();
        ChatRoomMember chatRoomMember = ChatRoomMember.create(createChatRoom, findUser, findUser, now);
        ChatRoomMember savedChatRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        return CreateChatRoomResponse.from(savedChatRoom, savedChatRoomMember);
    }
}
