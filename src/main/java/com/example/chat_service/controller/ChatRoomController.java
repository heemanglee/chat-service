package com.example.chat_service.controller;

import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import com.example.chat_service.dto.response.CommonApiResponse;
import com.example.chat_service.dto.response.chatroom.CreateChatRoomResponse;
import com.example.chat_service.security.dto.CustomUserDetails;
import com.example.chat_service.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("")
    public CommonApiResponse<CreateChatRoomResponse> createRoom(
            @Valid @RequestBody CreateChatRoomRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CreateChatRoomResponse response = chatRoomService.createRoom(userDetails.getEmail(), request);
        return CommonApiResponse.success(HttpStatus.CREATED, response);
    }
}
