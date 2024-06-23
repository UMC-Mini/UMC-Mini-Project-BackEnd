package com.miniproject.demo.domain.account.converter;

import com.miniproject.demo.domain.account.dto.ProfileResponseDTO;
import com.miniproject.demo.domain.account.dto.UserRequestDTO;
import com.miniproject.demo.domain.account.dto.UserResponseDTO;
import com.miniproject.demo.domain.account.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserConverter {

    public static User toUser(UserRequestDTO.CreateUserDTO dto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
    }

    public static UserResponseDTO.UserPreviewDTO toUserPreviewDTO(User user){
        return UserResponseDTO.UserPreviewDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .updateAt(user.getUpdatedAt())
                .createAt(user.getCreatedAt())
                .build();
    }

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user){
        return UserResponseDTO.JoinResultDTO.builder()
                .id(user.getId())
                .createAt(user.getCreatedAt())
                .build();
    }

    public static ProfileResponseDTO toProfileResponseDTO(User user) {
        return ProfileResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
