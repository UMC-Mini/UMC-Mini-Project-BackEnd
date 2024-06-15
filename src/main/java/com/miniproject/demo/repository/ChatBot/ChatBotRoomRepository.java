package com.miniproject.demo.repository.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotRoomRepository extends JpaRepository <ChatBotRoom,String> {
}
