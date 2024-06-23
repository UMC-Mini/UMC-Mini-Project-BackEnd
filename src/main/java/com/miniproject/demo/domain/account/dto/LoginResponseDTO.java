package com.miniproject.demo.domain.account.dto;

import com.miniproject.demo.domain.account.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {

    private Long userId;

    private String userName;

    private String userNickname;

    private LocalDateTime createdAt;

    private String token;

    public static LoginResponseDTO from(User user, String token) {
        return LoginResponseDTO.builder()
                .userId(user.getId())
                .userName(user.getName())
                .userNickname(user.getNickname())
                .createdAt(LocalDateTime.now())
                .token(token)
                .build();
    }
}
