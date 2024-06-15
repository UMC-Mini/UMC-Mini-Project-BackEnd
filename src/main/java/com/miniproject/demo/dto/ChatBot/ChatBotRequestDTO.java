package com.miniproject.demo.dto.ChatBot;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public class ChatBotRequestDTO {

    @Getter
    public static class CreateChatBotDTO{
        @NotEmpty(message = "Name cannot be empty")
        private String name;
        @NotEmpty(message = "Phone number cannot be empty")
        private String phoneNumber;
    }
}
