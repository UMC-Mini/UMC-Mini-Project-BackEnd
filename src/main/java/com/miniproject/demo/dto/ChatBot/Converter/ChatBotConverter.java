package com.miniproject.demo.dto.ChatBot.Converter;


import com.miniproject.demo.domain.ChatBot.ChatBotMessage;
import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;

public class ChatBotConverter {

    public static ChatBotUser toUser (ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO){
        return ChatBotUser.builder()
                .name(createChatBotDTO.getName())
                .phoneNumber(createChatBotDTO.getPhoneNumber())
                .build();
    }

    public static ChatBotResponseDTO.CreateChatBotResultDTO toCreateChatBotResultDTO (ChatBotRoom chatBotRoom){
        return ChatBotResponseDTO.CreateChatBotResultDTO.builder()
                .userId(chatBotRoom.getChatBotUser().getId())
                .roomId(chatBotRoom.getId())
                .createAt(chatBotRoom.getCreatedAt())
                .build();
    }

    public static ChatBotMessage toChatBotMessage (ChatBotRequestDTO.ChatBotMessageDTO chatBotMessageDTO,ChatBotRoom chatBotRoom){
        return ChatBotMessage.builder()
                .content(chatBotMessageDTO.getContent())
                .imageUrl(chatBotMessageDTO.getImageUrl())
                .sender(chatBotMessageDTO.getSender())
                .chatBotRoom(chatBotRoom)
                .build();
    }
}
