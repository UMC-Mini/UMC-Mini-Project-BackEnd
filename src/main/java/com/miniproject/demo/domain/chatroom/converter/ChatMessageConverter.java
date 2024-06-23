package com.miniproject.demo.domain.chatroom.converter;

import com.miniproject.demo.domain.account.service.UserService;
import com.miniproject.demo.domain.chatroom.dto.ChatMessageResponseDTO;
import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.global.response.code.BaseEntity;
import lombok.RequiredArgsConstructor;


public class ChatMessageConverter extends BaseEntity {

    public static ChatMessageResponseDTO toChatRoomResponseDTO(ChatMessage chatMessage) {

        return ChatMessageResponseDTO.builder()
                .name(chatMessage.getSender().getNickname())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
