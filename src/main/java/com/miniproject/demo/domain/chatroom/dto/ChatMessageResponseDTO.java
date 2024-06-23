package com.miniproject.demo.domain.chatroom.dto;

import com.miniproject.demo.global.response.code.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatMessageResponseDTO {
    String name;
    String content;
    LocalDateTime createdAt;
}
