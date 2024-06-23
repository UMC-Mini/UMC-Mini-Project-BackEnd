package com.miniproject.demo.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatJoinRequestDTO {
    Long roomId;
    Long userId;
}
