package com.miniproject.demo.repository.ChatBot;

import com.miniproject.demo.domain.ChatBot.ChatBotUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatBotUserRepository extends JpaRepository<ChatBotUser, Long> {
    Optional<ChatBotUser> findByNameAndPhoneNumber(String name, String phoneNumber);
}
