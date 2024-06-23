package com.miniproject.demo.domain.chatroom.service;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatService {


    //메세지 생성 전 코드
//    public ChatMessage saveMessage(ChatMessage chatMessage) {
//        return chatMessageRepository.save(chatMessage);
//    }

    //메세지 조회 전 코드
//    public List<ChatMessage> getMessageById(Long roomId) {
//        return chatMessageRepository.findByChatroomId(roomId);
//    }

    //채팅 룸 객체 받아오기 전 코드
//    public Chatroom getChatroomById(Long chatroomId) {
//        Optional<Chatroom> chatroomOptional = chatRoomRepository.findById(chatroomId);
//        return chatroomOptional.orElseThrow(() -> new RuntimeException("Chatroom not found with id " + chatroomId));
//    }

    //채팅방 생성 전 코드
//    public Chatroom createRoom(String roomName, Long userCount) {
//        Chatroom chatroom = Chatroom.builder()
//                .roomName(roomName)
//                .userCount(userCount)
//                .build();
//        return chatRoomRepository.save(chatroom);
//    }

    //채팅방 생성
    Chatroom createRoom(String roomName, Long userCount);

    //메세지 저장
    ChatMessage saveMessage(ChatMessage chatMessage);

    //메세지 조회
    List<ChatMessage> getMessageById(Long roomId);

    //채팅 방 객체로 받아오기
    Chatroom getChatRoomById(Long roomId);

    //채팅방 전체 조회
    List<Chatroom> getAllChatRoom();

    //채팅방 내의 유저 닉네임 전체 조회
    List<String> getAllUser(Long roomId);
}
