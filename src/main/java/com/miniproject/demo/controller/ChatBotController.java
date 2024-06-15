package com.miniproject.demo.controller;

import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
import com.miniproject.demo.global.response.BaseResponse;
import com.miniproject.demo.global.response.code.BaseCode;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import com.miniproject.demo.global.response.code.status.SuccessStatus;
import com.miniproject.demo.service.ChatBot.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping("")
    public BaseResponse<ChatBotResponseDTO.CreateChatBotResultDTO> createChatBot(@RequestBody ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO){
        try{
            return BaseResponse.of(SuccessStatus.CHATBOTROOM_CREATE_SUCCESS,chatBotService.createChatBot(createChatBotDTO));
        }catch(NoSuchElementException e){
            return BaseResponse.onFailure(ErrorStatus.CHATBOT_ROOM_CREATE_FAIL.getMessage(), ErrorStatus.CHATBOT_ROOM_CREATE_FAIL.getCode(), null);
        }
    }


}
