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

    @ManyToOne
    @JoinColumn(name = "chatbot_user_id")
    private ChatBotUser chatBotUser;

    @OneToMany(mappedBy = "chatBotRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatBotMessage> chatBotMessages = new ArrayList<>();

    public void setUser(ChatBotUser user) {
        this.chatBotUser = user;
    }

    public Long getId() {
        return id;
    }

    public ChatBotUser getChatBotUser(){
        return chatBotUser;
    }
    public ChatBotRoom(ChatBotUser user) {
        this.chatBotUser = user;
    }

}
