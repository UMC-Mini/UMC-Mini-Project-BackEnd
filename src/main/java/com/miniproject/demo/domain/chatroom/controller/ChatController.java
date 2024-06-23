package com.miniproject.demo.domain.chatroom.controller;

import com.miniproject.demo.domain.chatroom.converter.ChatMessageConverter;
import com.miniproject.demo.domain.chatroom.converter.ChatRoomConverter;

import com.miniproject.demo.domain.chatroom.dto.ChatJoinRequestDTO;
import com.miniproject.demo.domain.chatroom.dto.ChatMessageResponseDTO;
import com.miniproject.demo.domain.chatroom.dto.ChatRoomRequestDTO;
import com.miniproject.demo.domain.chatroom.dto.ChatRoomResponseDTO;
import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import com.miniproject.demo.domain.chatroom.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public Map<String, Object> sendMessage(Principal principal, Map<String, Object> message) {
        Long sender = Long.parseLong(message.get("sender").toString());
        String content = message.get("content").toString();
        Integer chatroomIdInteger = (Integer) message.get("chatroom");
        long roomId = chatroomIdInteger.longValue();

        Chatroom room = chatService.getChatRoomById(roomId);

        ChatMessage message1 = ChatMessage.builder()
                .content(content)
                .chatroom(room)
                .build();
        System.out.println(message1);
        ChatMessage chat = chatService.saveMessage(message1, sender);

        Map<String, Object> result = new HashMap<>();

        result.put("id", chat.getId());
        result.put("name", chat.getSender().getNickname());
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
    public Chatroom createRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
        return chatService.createRoom(chatRoomRequestDTO.getRoomName(), chatRoomRequestDTO.getUserCount(), chatRoomRequestDTO.getPassword());
    }

    //채팅방 전체 조회
    @GetMapping("/chatrooms")
    public List<ChatRoomResponseDTO> getAllChatRoom() {
        return chatService.getAllChatRoom().stream().map(ChatRoomConverter::toChatRoomResponseDTO).toList();
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

    //채팅방 입장
    @PostMapping("chatroom/join")
    public Long joinRoom(@RequestBody ChatJoinRequestDTO chatJoinRequestDTO) {
        return chatService.joinRoom(chatJoinRequestDTO.getRoomId(), chatJoinRequestDTO.getUserId());
    }

    //채팅방 채팅 메세지 기록 조회
    @GetMapping("/chattinglist/{roomId}")
    public List<ChatMessageResponseDTO> getAllMessage(@PathVariable Long roomId) {
        return chatService.getAllMessage(roomId).stream().map(ChatMessageConverter::toChatRoomResponseDTO).toList();
    }


}



