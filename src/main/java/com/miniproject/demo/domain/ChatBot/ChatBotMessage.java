package com.miniproject.demo.domain.ChatBot;

import com.miniproject.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatBotMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatbot_message_id")
    private Long id;

    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    private String sender;


    @ManyToOne
    @JoinColumn(name = "chatbot_room_id")
    private ChatBotRoom chatBotRoom;
}
