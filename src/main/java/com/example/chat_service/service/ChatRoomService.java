package com.example.chat_service.service;

import com.example.chat_service.domain.ChatRoom;
import com.example.chat_service.domain.ChatRoomMember;
import com.example.chat_service.domain.User;
import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import com.example.chat_service.dto.response.chatroom.CreateChatRoomResponse;
import com.example.chat_service.dto.response.chatroom.GetChatRoomResponse;
import com.example.chat_service.exception.DuplicateTitleException;
import com.example.chat_service.exception.NotFoundChatRoom;
import com.example.chat_service.exception.UnauthorizedAccessException;
import com.example.chat_service.repository.ChatRoomMemberRepository;
import com.example.chat_service.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
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

    @Transactional(readOnly = true)
    public GetChatRoomResponse findChatRoom(String email, Long roomId) {
        User findUser = userService.findByEmail(email);
        ChatRoom findChatRoom = findById(roomId);

        // 채팅방에 속한 사용자인지 확인
        boolean isMember = chatRoomMemberRepository.existsByChatRoomIdAndUserId(findChatRoom.getId(), findUser.getId());
        if(isMember) {
            return GetChatRoomResponse.of(findChatRoom);
        }

        log.warn("AccessDenied(chat_room): userId:{}, roomId={}", findUser.getId(), findChatRoom.getId());
        throw new UnauthorizedAccessException("User is not a member of this chat room Id: " + roomId);
    }

    public ChatRoom findById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundChatRoom("Not Found Chat Room Id: " + roomId));
    }
}
