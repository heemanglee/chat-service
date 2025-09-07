package com.example.chat_service.controller;

import com.example.chat_service.dto.request.chatroom.CreateChatRoomRequest;
import com.example.chat_service.dto.response.CommonApiResponse;
import com.example.chat_service.dto.response.chatroom.CreateChatRoomResponse;
import com.example.chat_service.dto.response.chatroom.GetChatRoomResponse;
import com.example.chat_service.security.dto.CustomUserDetails;
import com.example.chat_service.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{chat_room_id}")
    public CommonApiResponse<GetChatRoomResponse> getChatRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("chat_room_id") Long roomId
    ) {
        GetChatRoomResponse response = chatRoomService.findChatRoom(userDetails.getEmail(), roomId);
        return CommonApiResponse.success(response);
    }
}
