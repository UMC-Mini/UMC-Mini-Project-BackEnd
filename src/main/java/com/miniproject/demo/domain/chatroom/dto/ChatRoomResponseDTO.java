package com.miniproject.demo.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomResponseDTO {
    Long id;
    String roomName;
    Long userCount;
    Boolean isPassword;
}
