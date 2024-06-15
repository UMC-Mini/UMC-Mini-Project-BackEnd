package com.miniproject.demo.repository.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotMessageRepository extends JpaRepository<ChatBotMessage, String> {
}
