package com.miniproject.demo.domain.chatroom.controller;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import com.miniproject.demo.domain.chatroom.repository.ChatMessageRepository;
import com.miniproject.demo.domain.chatroom.repository.ChatRoomRepository;
import com.miniproject.demo.domain.chatroom.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chatrooms")
    public Chatroom createRoom(@RequestParam String name, @RequestParam Integer userCount) {
        return chatService.createRoom(name, userCount);
    }

    //메세지 보내기 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Map<String, Object> message) {
        String sender = message.get("sender").toString();
        String content = message.get("content").toString();
        Integer chatroomIdInteger = (Integer) message.get("chatroom");
        long chatroomId = chatroomIdInteger.longValue();

        Chatroom room = chatService.getChatroomById(chatroomId);

        ChatMessage message1 = ChatMessage.builder()
                .sender(sender)
                .content(content)
                .chatroom(room)
                .build();
        System.out.println(message1);
        ChatMessage chat = chatService.saveMessage(message1);

        Map<String, Object> result = new HashMap<>();

        result.put("id", chat.getId());
        result.put("sender", chat.getSender());
        result.put("content", chat.getContent());
        result.put("chatroom_id", chat.getChatroom().getId());
        return result;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        return chatMessage;
    }

}

@RestController
class ChatHistoryController {
    @Autowired
    private ChatService chatService;

    //채팅 기록 전체 조회
    @GetMapping("/chat/history/{roomId}")
    public List<ChatMessage> getChatHistory(@PathVariable Long roomId) {
        return chatService.getMessageById(roomId);
    }
}
