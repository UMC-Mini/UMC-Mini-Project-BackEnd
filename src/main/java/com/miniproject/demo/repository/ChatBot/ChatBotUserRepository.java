package com.miniproject.demo.repository.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotUserRepository extends JpaRepository<ChatBotUser, Long> {
}
