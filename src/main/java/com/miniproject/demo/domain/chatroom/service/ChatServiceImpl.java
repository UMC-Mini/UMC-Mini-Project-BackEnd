package com.miniproject.demo.domain.chatroom.service;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import com.miniproject.demo.domain.chatroom.repository.ChatMessageRepository;
import com.miniproject.demo.domain.chatroom.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

//    @Autowired
//    SimpMessagingTemplate template;

    public ChatServiceImpl(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    //채팅방 생성
    @Override
    public Chatroom createRoom(String roomName, Long userCount) {
        Chatroom chatroom = Chatroom.builder()
                .roomName(roomName)
                .userCount(userCount)
                .build();
        return chatRoomRepository.save(chatroom);
    }

    //메세지 저장
    @Override
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    //메세지 방 id로 조회
    @Override
    public List<ChatMessage> getMessageById(Long roomId) {
        return chatMessageRepository.findByChatroomId(roomId);
    }

    //채팅방 객체 조회
    @Override
    public Chatroom getChatRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방 안찾아짐" + roomId));
    }

    //채팅방 전체 조회
    @Override
    public List<Chatroom> getAllChatRoom() {
        return chatRoomRepository.findAll();
    }



}
