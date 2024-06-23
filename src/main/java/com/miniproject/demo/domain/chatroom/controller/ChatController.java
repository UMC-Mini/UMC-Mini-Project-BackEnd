package com.miniproject.demo.domain.chatroom.controller;

import com.miniproject.demo.domain.chatroom.dto.ChatRoomDTO;
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

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //메세지 보내기 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Map<String, Object> message) {
        String sender = message.get("sender").toString();
        String content = message.get("content").toString();
        Integer chatroomIdInteger = (Integer) message.get("chatroom");
        long roomId = chatroomIdInteger.longValue();

        Chatroom room = chatService.getChatRoomById(roomId);

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


    //채팅방 생성 컨트롤러
    @PostMapping("/chatrooms")
    public Chatroom createRoom(@RequestBody ChatRoomDTO chatRoomDTO) {
        return chatService.createRoom(chatRoomDTO.getRoomName(), chatRoomDTO.getUserCount());
    }

    //채팅방 전체 조회
    @GetMapping("chatrooms")
    public List<Chatroom> getAllChatRoom() {
        return chatService.getAllChatRoom();
    }

    //채팅 기록 전체 조회
    @GetMapping("/chat/history/{roomId}")
    public List<ChatMessage> getChatHistory(@PathVariable Long roomId) {
        return chatService.getMessageById(roomId);
    }

    //채팅방 내부 유저 전체 조회
    @GetMapping("chatroom/users/{roomId}")
    public List<String> getAllUser(@PathVariable Long roomId) {
        return chatService.getAllUser(roomId);
    }

}



