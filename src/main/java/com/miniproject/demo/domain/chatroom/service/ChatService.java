package com.miniproject.demo.domain.chatroom.service;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import com.miniproject.demo.domain.chatroom.repository.ChatMessageRepository;
import com.miniproject.demo.domain.chatroom.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    SimpMessagingTemplate template;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessageById(Long roomId) {
        return chatMessageRepository.findByChatroomId(roomId);
    }

    public Chatroom getChatroomById(Long chatroomId) {
        Optional<Chatroom> chatroomOptional = chatRoomRepository.findById(chatroomId);
        return chatroomOptional.orElseThrow(() -> new RuntimeException("Chatroom not found with id " + chatroomId));
    }

    public Chatroom createRoom(String name, Integer userCount) {
        Chatroom chatroom = Chatroom.builder()
                .name(name)
                .userCount(userCount)
                .build();

        return chatroom;
    }
}
