package com.miniproject.demo.domain.chatroom.entity;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.global.response.code.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sender_id")
    private User sender;

//    private String roomId;

    //채팅방이랑 채팅 메세지 1대다 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    public void setUser(User sender) {
        this.sender = sender;
    }
}
