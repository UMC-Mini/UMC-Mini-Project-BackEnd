package com.miniproject.demo.dto.ChatBot;

import lombok.*;
import java.util.List;
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChatBotMessageListDTO{
        private List<ChatBotRequestDTO.ChatBotMessageDTO> messages;
        private Long nextCursor;
        private boolean isLast;

    }

}
