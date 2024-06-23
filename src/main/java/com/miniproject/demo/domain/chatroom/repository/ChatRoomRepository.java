package com.miniproject.demo.domain.chatroom.repository;

import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<Chatroom, Long> {
}
