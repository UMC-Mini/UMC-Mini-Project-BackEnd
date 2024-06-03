package com.miniproject.demo.service;

import com.miniproject.demo.domain.ChatMessage;
import com.miniproject.demo.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    SimpMessagingTemplate template;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
//        template.convertAndSend("/topic/public" + sessionId, message);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
