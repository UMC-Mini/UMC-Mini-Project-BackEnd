package com.miniproject.demo.dto.ChatBot;

import lombok.*;
import java.time.LocalDateTime;

public class ChatBotResponseDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateChatBotResultDTO {
        private Long roomId;
        private Long userId;
        private LocalDateTime createAt;
    }

}
