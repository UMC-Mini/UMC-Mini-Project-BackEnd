package com.miniproject.demo.domain.chatroom.repository;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatroomId(Long chatroomId);
    List<ChatMessage> findAllByChatroomOrderByCreatedAtDesc(Chatroom chatroom);
}
