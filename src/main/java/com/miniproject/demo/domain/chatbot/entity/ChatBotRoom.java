package com.miniproject.demo.domain.chatbot.entity;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.global.response.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_bot_room")
public class ChatBotRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_bot_room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_bot_room_user_id")
    private User user;

}
