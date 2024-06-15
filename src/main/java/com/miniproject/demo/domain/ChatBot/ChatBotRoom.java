package com.miniproject.demo.domain.ChatBot;

import com.miniproject.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatBotRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatbot_room_id")
    private Long id;

    @OneToMany(mappedBy = "chatBotRoom")
    private List<ChatBotUser> chatBotUsers = new ArrayList<>();


}
