package com.miniproject.demo.dto.ChatBot;

import lombok.Getter;

public class ChatBotRequestDTO {

    @Getter
    public static class CreateChatBotUserDTO{
        private String name;
        private String phoneNumber;
    }
}
