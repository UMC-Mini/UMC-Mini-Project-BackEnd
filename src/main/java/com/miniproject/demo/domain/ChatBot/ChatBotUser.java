package com.miniproject.demo.domain.ChatBot;


import com.miniproject.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatBotUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatbot_user_id")
    private Long id;

    private String name;

    private String phoneNumber;

    @OneToMany(mappedBy = "chatBotUser")
    private List<ChatBotRoom> chatBotRooms = new ArrayList<>();

    public Long getId() {
        return id;
    }
}
