package com.miniproject.demo.domain.account.entity;

import com.miniproject.demo.domain.chatbot.entity.ChatBotRoom;
import com.miniproject.demo.domain.mapping.UserChatRoom;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.global.response.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; // pk

    @Column(name = "user_name")
    private String name; // 사용자 이름

    @Column(name = "user_email", nullable = false, unique = true)
    private String email; // 사용자 이메일 (학번 대신 이메일로 로그인)

    @Column(name = "user_password")
    private String password; // 사용자 비밀번호

    @Column(name = "user_nickname")
    private String nickname;

    private String role;

    /*@OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>(); // 게시물과의 연관관계*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChatRoom> userChatRooms = new ArrayList<>(); // 채팅방과의 다대다 관계 매핑 구현

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatBotRoom> chatBotRooms = new ArrayList<>();

    public void setRole(String role){
        this.role = role;
    }

    public void update(String password, String nickname, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
        this.nickname = nickname;
    }

}
