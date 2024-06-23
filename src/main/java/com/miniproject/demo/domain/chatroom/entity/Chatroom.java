package com.miniproject.demo.domain.chatroom.entity;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.mapping.UserChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    //@Column(nullable = false)
    private Long userCount; //채팅방 인원수


    //채팅방 채팅 다대일 매핑
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatMessage> chatMessages = new ArrayList<>();

    //채팅방 유저 매핑테이블로 매핑
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserChatRoom> userChatRooms = new ArrayList<>();
}
