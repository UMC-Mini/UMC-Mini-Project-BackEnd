package com.miniproject.demo.controller;

import com.miniproject.demo.domain.ChatMessage;
import com.miniproject.demo.dto.ChatMessageDTO;
import com.miniproject.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    //메세지 보내기 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Map<String, Object> message) {
//        System.out.println(message);
        String sender = message.get("sender").toString();
        String content = message.get("content").toString();
        ChatMessage message1 = ChatMessage.builder()
                .sender(sender)
                .content(content)
                .build();
        ChatMessage chat = chatService.saveMessage(message1);
        Map<String, Object> result = new HashMap<>();
        result.put("id", chat.getId());
        result.put("sender", chat.getSender());
        result.put("content", chat.getContent());
        return result;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        return chatMessage;
    }

}

@RestController
class ChatHistoryController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat/history")
    public List<ChatMessage> getChatHistory() {
        return chatService.getAllMessages();
    }
}
