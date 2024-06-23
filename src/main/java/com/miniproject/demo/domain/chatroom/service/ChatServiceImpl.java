package com.miniproject.demo.domain.chatroom.service;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.account.repository.UserRepository;
import com.miniproject.demo.domain.chatroom.entity.ChatMessage;
import com.miniproject.demo.domain.chatroom.entity.Chatroom;
import com.miniproject.demo.domain.chatroom.repository.ChatMessageRepository;
import com.miniproject.demo.domain.chatroom.repository.ChatRoomRepository;
import com.miniproject.demo.domain.chatroom.repository.UserChatRepository;
import com.miniproject.demo.domain.mapping.UserChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final UserChatRepository userChatRepository;

    private final UserRepository userRepository;

//    @Autowired
//    SimpMessagingTemplate template;

    public ChatServiceImpl(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, UserChatRepository userChatRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userChatRepository = userChatRepository;
        this.userRepository = userRepository;
    }

    //채팅방 생성
    @Override
    public Chatroom createRoom(String roomName, Long userCount, String password) {
        Chatroom chatroom = Chatroom.builder()
                .roomName(roomName)
                .userCount(userCount)
                .password(password)
                .build();
        return chatRoomRepository.save(chatroom);
    }

    //메세지 저장
    @Override
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    //메세지 방 id로 조회
    @Override
    public List<ChatMessage> getMessageById(Long roomId) {
        return chatMessageRepository.findByChatroomId(roomId);
    }

    //채팅방 객체 조회
    @Override
    public Chatroom getChatRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방 안찾아짐" + roomId));
    }

    //채팅방 전체 조회
    @Override
    public List<Chatroom> getAllChatRoom() {
        return chatRoomRepository.findAll();
    }

    //채팅방 내부에 있는 유저 리스트 전체 조회(네임)
    @Override
    public List<String> getAllUser(Long roomId) {
        Chatroom chatroom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방" + roomId));
        return chatroom.getUserChatRooms().stream().map(room -> room.getUser().getName()).toList();
    }

    //채팅방 입장
    @Override
    public Long joinRoom(Long roomId, Long userId) {
        Chatroom chatroom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방" + roomId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저" + userId));
        UserChatRoom userChatRoom = UserChatRoom.builder().chatroom(chatroom).user(user).build();
        userChatRepository.save(userChatRoom);
        return userChatRoom.getUser().getId();
    }
}

//    @Override
//    public Chatroom joinRoom(Long roomId) {
//        Chatroom chatroom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방" + roomId));
//    }

