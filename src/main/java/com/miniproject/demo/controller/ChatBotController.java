package com.miniproject.demo.controller;

import com.miniproject.demo.service.ChatBot.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
    private final ChatBotService chatBotService;



}
