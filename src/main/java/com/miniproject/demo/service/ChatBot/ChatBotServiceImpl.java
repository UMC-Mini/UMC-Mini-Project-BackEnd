package com.miniproject.demo.service.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotMessage;
import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
import com.miniproject.demo.global.error.handler.ChatBotRoomHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import com.miniproject.demo.repository.ChatBot.ChatBotMessageRepository;
import com.miniproject.demo.repository.ChatBot.ChatBotRoomRepository;
import com.miniproject.demo.repository.ChatBot.ChatBotUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.miniproject.demo.dto.ChatBot.Converter.ChatBotConverter.*;


@Service
@RequiredArgsConstructor
@Transactional

public class ChatBotServiceImpl implements  ChatBotService {
    private final ChatBotUserRepository chatBotUserRepository;
    private final ChatBotRoomRepository chatBotRoomRepository;
    private final ChatBotMessageRepository chatBotMessageRepository;
    @Autowired
    SimpMessagingTemplate template;

    @Override
    //To do : 이미 있는 사용자인지 검사
    public ChatBotResponseDTO.CreateChatBotResultDTO createChatBot(ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO){
        Optional<ChatBotUser> isUser = chatBotUserRepository.findByNameAndPhoneNumber(createChatBotDTO.getName(), createChatBotDTO.getPhoneNumber());
        if(isUser.isPresent()){
            return toCreateChatBotResultDTO(isUser.get().getChatBotRoom());
        }
        else{
            ChatBotUser chatBotUser = toUser(createChatBotDTO);
            chatBotUserRepository.save(chatBotUser);

            ChatBotRoom chatBotRoom = new ChatBotRoom(chatBotUser);
            chatBotRoomRepository.save(chatBotRoom);

            return toCreateChatBotResultDTO(chatBotRoom);
        }

    }

    @Override
    public void sendMessage (ChatBotRequestDTO.ChatBotMessageDTO chatBotMessageDTO){
        try{
            ChatBotRoom chatBotRoom = chatBotRoomRepository.findById(chatBotMessageDTO.getChatBotRoomId())
                    .orElseThrow(()-> new ChatBotRoomHandler(ErrorStatus.CHATBOT_ROOM_NOT_FOUND));

            ChatBotMessage chatMessage = toChatBotMessage(chatBotMessageDTO,chatBotRoom);
            chatBotMessageRepository.save(chatMessage);
            template.convertAndSend("/topic/" + chatBotMessageDTO.getChatBotRoomId(), chatBotMessageDTO);
        }catch(NoSuchElementException e){
            template.convertAndSend("/topic/" + chatBotMessageDTO.getChatBotRoomId(), ErrorStatus._BAD_REQUEST);
        }
    }

    @Override
    public ChatBotResponseDTO.ChatBotMessageListDTO getChatBotMessages (Long roomId, Long cursor, int pageSize){
        ChatBotRoom chatBotRoom = chatBotRoomRepository.findById(roomId)
                .orElseThrow(()-> new ChatBotRoomHandler(ErrorStatus.CHATBOT_ROOM_NOT_FOUND));
        Pageable pageable = PageRequest.of(0,pageSize);
        List<ChatBotMessage> messages = chatBotMessageRepository.findByChatBotRoomAndIdGreaterThan(chatBotRoom,cursor,pageable);
        Long nextCursor = messages.isEmpty()?null:messages.get(messages.size()-1).getId();
        boolean isLast = messages.size()<pageSize;

        return toChatBotMessageList(messages,nextCursor,isLast);

    }

    @Override
    public void deleteChat (Long roomId){
        ChatBotRoom chatBotRoom = chatBotRoomRepository.findById(roomId)
                .orElseThrow(()-> new ChatBotRoomHandler(ErrorStatus.CHATBOT_ROOM_NOT_FOUND));
        ChatBotUser chatBotUser = chatBotRoom.getChatBotUser();
        chatBotUserRepository.delete(chatBotUser);
    }
}
