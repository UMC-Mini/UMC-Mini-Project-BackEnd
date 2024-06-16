package com.miniproject.demo.controller;

import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
import com.miniproject.demo.global.response.BaseResponse;
import com.miniproject.demo.global.response.code.BaseCode;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import com.miniproject.demo.global.response.code.status.SuccessStatus;
import com.miniproject.demo.service.ChatBot.ChatBotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping("")
    @Operation(summary = "상담 채팅 시작하기", description = "이름과 전화번호를 입력하면 사용자와 상담 채팅방이 생성됩니다." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "CREATED", description = "상담채팅방 생성 성공"),
            @ApiResponse(responseCode = "NOT_FOUND", description = "채팅방을 찾을 수 없습니다.")
    })
    public BaseResponse<ChatBotResponseDTO.CreateChatBotResultDTO> createChatBot(@RequestBody ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO){
        try{
            return BaseResponse.of(SuccessStatus.CHATBOT_ROOM_CREATE_SUCCESS,chatBotService.createChatBot(createChatBotDTO));
        }catch(NoSuchElementException e){
            return BaseResponse.onFailure(ErrorStatus.CHATBOT_ROOM_CREATE_FAIL.getMessage(), ErrorStatus.CHATBOT_ROOM_CREATE_FAIL.getCode(), null);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/{chatBotRoomId}")
    public void sendMessage(@Payload ChatBotRequestDTO.ChatBotMessageDTO chatBotMessageDTO){
        chatBotService.sendMessage(chatBotMessageDTO);

    }



    @GetMapping("/room/{roomId}/messages")
    @Operation(summary = "채팅 내역 불러오기", description = "채팅메시지 불러오기 성공" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "상담채팅방 생성 성공"),
            @ApiResponse(responseCode = "NOT_FOUND", description = "채팅 메시지를 찾을 수 없습니다.")
    })
    public BaseResponse<ChatBotResponseDTO.ChatBotMessageListDTO> getChatMessages(@PathVariable Long roomId,
                                                                                  @RequestParam(name = "cursor") Long cursor,
                                                                                  @RequestParam(name = "pageSize") Integer pageSize){
        try{
            return BaseResponse.of(SuccessStatus.CHAT_MESSAGE_FETCH_SUCCESS,chatBotService.getChatBotMessages(roomId,cursor,pageSize));
        }catch(NoSuchElementException e){
            return BaseResponse.onFailure(ErrorStatus.CHATBOT_MESSAGE_NOT_FOUND.getMessage(), ErrorStatus.CHATBOT_MESSAGE_NOT_FOUND.getCode(), null);
        }
    }

    @DeleteMapping("/room/{roomId}")
    @Operation(summary = "상담 종료", description = "상담종료하기를 누르면 사용자, 상담 채팅방, 채팅 내용이 모두 삭제됩니다." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "상담채팅방 삭제 성공"),
            @ApiResponse(responseCode = "NOT_FOUND", description = "정보를 찾을 수 없습니다.")
    })
    public BaseResponse<Void>deleteChat(@PathVariable Long roomId){
        try{
            chatBotService.deleteChat(roomId);
            return BaseResponse.of(SuccessStatus.CHATBOT_DELETE_SUCCESS,null);
        }catch(NoSuchElementException e){
            return BaseResponse.onFailure(ErrorStatus.CHATBOT_ROOM_NOT_FOUND.getMessage(), ErrorStatus.CHATBOT_ROOM_NOT_FOUND.getCode(), null);
        }

    }


}
