package com.miniproject.demo.service.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
import com.miniproject.demo.repository.ChatBot.ChatBotRoomRepository;
import com.miniproject.demo.repository.ChatBot.ChatBotUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniproject.demo.dto.ChatBot.Converter.ChatBotConverter.toCreateChatBotResultDTO;
import static com.miniproject.demo.dto.ChatBot.Converter.ChatBotConverter.toUser;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatBotServiceImpl implements  ChatBotService {
    private final ChatBotUserRepository chatBotUserRepository;
    private final ChatBotRoomRepository chatBotRoomRepository;

    @Override
    public ChatBotResponseDTO.CreateChatBotResultDTO createChatBot(ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO){
        ChatBotUser chatBotUser = toUser(createChatBotDTO);
        chatBotUserRepository.save(chatBotUser);

        ChatBotRoom chatBotRoom = new ChatBotRoom(chatBotUser);
        chatBotRoomRepository.save(chatBotRoom);

        return toCreateChatBotResultDTO(chatBotRoom);
    }
}
