package com.miniproject.demo.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomRequestDTO {
        String roomName;
        Long userCount;
        String password;
}
