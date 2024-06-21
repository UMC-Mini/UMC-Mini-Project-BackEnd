package com.miniproject.demo.domain.chatroom.entity;

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

    @Column(nullable = false)
    private int userCount; //채팅방 인원수

    //채팅방 채팅 다대일 매핑
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

}
