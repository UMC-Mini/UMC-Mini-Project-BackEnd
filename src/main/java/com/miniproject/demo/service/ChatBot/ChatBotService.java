package com.miniproject.demo.service.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;

public interface ChatBotService {
    ChatBotResponseDTO.CreateChatBotResultDTO createChatBot(ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO);
    void sendMessage(ChatBotRequestDTO.ChatBotMessageDTO chatBotMessageDTO);
    ChatBotResponseDTO.ChatBotMessageListDTO getChatBotMessages (Long roomId, Long cursor, int pageSize);
}
