package com.miniproject.demo.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatMessageDTO {
    String sender;
    String content;
}
