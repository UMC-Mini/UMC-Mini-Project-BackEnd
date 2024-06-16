package com.miniproject.demo.dto.ChatBot.Converter;


import com.miniproject.demo.domain.ChatBot.ChatBotMessage;
import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
import com.miniproject.demo.dto.ChatMessageDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ChatBotResponseDTO.ChatBotMessageListDTO toChatBotMessageList(List<ChatBotMessage> messages, Long nextCursor, boolean isLast) {
        List<ChatBotRequestDTO.ChatBotMessageDTO> chatBotMessages = messages.stream()
                .map(message -> ChatBotRequestDTO.ChatBotMessageDTO.builder()
                        .content(message.getContent())
                        .imageUrl(message.getImageUrl())
                        .sender(message.getSender())
                        .chatBotRoomId(message.getChatBotRoom().getId())
                        .build())
                .collect(Collectors.toList());

        return ChatBotResponseDTO.ChatBotMessageListDTO.builder()
                .messages(chatBotMessages)
                .nextCursor(nextCursor)
                .isLast(isLast)
                .build();
    }



}
