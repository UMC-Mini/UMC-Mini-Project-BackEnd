package com.miniproject.demo.domain.chatroom.converter;

import com.miniproject.demo.domain.chatroom.dto.ChatRoomResponseDTO;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;

public class ChatRoomConverter {
    public static ChatRoomResponseDTO toChatRoomResponseDTO(Chatroom chatRoom) {
        return ChatRoomResponseDTO.builder()
                .id(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .isPassword(chatRoom.getPassword() != null)
                .userCount(chatRoom.getUserCount())
                .build();
    }
}
