package com.miniproject.demo.repository.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotMessage;
import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatBotMessageRepository extends JpaRepository<ChatBotMessage, Long> {
    @Query("SELECT m FROM ChatBotMessage m WHERE m.chatBotRoom = :chatBotRoom AND (:cursor IS NULL OR m.id > :cursor) ORDER BY m.id ASC")
    List<ChatBotMessage> findByChatBotRoomAndIdGreaterThan(@Param("chatBotRoom") ChatBotRoom chatBotRoom, @Param("cursor") Long cursor, Pageable pageable);
}
