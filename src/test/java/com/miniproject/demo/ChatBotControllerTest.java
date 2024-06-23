//package com.miniproject.demo;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.miniproject.demo.controller.ChatBotController;
//import com.miniproject.demo.dto.ChatBot.ChatBotRequestDTO;
//import com.miniproject.demo.dto.ChatBot.ChatBotResponseDTO;
//import com.miniproject.demo.global.response.code.status.SuccessStatus;
//import com.miniproject.demo.service.ChatBot.ChatBotService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ChatBotController.class)
//public class ChatBotControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ChatBotService chatBotService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testCreateChatBot() throws Exception {
//        ChatBotRequestDTO.CreateChatBotDTO createChatBotDTO = new ChatBotRequestDTO.CreateChatBotDTO();
//        // Set properties for createChatBotDTO
//
//        ChatBotResponseDTO.CreateChatBotResultDTO resultDTO = new ChatBotResponseDTO.CreateChatBotResultDTO();
//        // Set properties for resultDTO
//
//        Mockito.when(chatBotService.createChatBot(Mockito.any(ChatBotRequestDTO.CreateChatBotDTO.class)))
//                .thenReturn(resultDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/chatbot")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createChatBotDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value(SuccessStatus.CHATBOT_ROOM_CREATE_SUCCESS.getMessage()));
//    }
//
//    @Test
////    public void testSendMessage() throws Exception {
//        ChatBotRequestDTO.ChatBotMessageDTO chatBotMessageDTO = new ChatBotRequestDTO.ChatBotMessageDTO();
//        // Set properties for chatBotMessageDTO
////
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/chatbot/sendMessage")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(chatBotMessageDTO)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetChatMessages() throws Exception {
//        Long roomId = 1L;
//        Long cursor = 0L;
//        Integer pageSize = 10;
//
//        ChatBotResponseDTO.ChatBotMessageListDTO messageListDTO = new ChatBotResponseDTO.ChatBotMessageListDTO();
//        // Set properties for messageListDTO
//
//        Mockito.when(chatBotService.getChatBotMessages(roomId, cursor, pageSize))
//                .thenReturn(messageListDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/chatbot/room/{roomId}/messages", roomId)
//                        .param("cursor", String.valueOf(cursor))
//                        .param("pageSize", String.valueOf(pageSize)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(SuccessStatus.CHAT_MESSAGE_FETCH_SUCCESS.getMessage()));
//    }
//
//    @Test
//    public void testDeleteChat() throws Exception {
//        Long roomId = 1L;
//
//        Mockito.doNothing().when(chatBotService).deleteChat(roomId);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/chatbot/room/{roomId}", roomId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(SuccessStatus.CHATBOT_DELETE_SUCCESS.getMessage()));
//    }
//}
