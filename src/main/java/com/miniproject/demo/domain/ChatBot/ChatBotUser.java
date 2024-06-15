package com.miniproject.demo.domain.ChatBot;


import com.miniproject.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatbot_room_id")
    private ChatBotRoom chatBotRoom;
}
