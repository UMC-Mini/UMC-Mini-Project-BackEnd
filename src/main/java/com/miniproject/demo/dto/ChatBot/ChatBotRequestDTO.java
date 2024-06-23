package com.miniproject.demo.dto.ChatBot;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatBotRequestDTO {

    @Getter
    public static class CreateChatBotDTO{
        @NotEmpty(message = "Name cannot be empty")
        private String name;
        @NotEmpty(message = "Phone number cannot be empty")
        private String phoneNumber;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChatBotMessageDTO{
        private String content;
//        private String imageUrl;
        private String sender;
        private Long chatBotRoomId;
    }
}
