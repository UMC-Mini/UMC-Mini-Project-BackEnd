package com.miniproject.demo.domain.chatroom.repository;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
