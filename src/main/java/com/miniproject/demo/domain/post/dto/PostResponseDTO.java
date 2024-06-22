package com.miniproject.demo.domain.post.dto;

import com.miniproject.demo.domain.account.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        private Long id;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewResultDTO {
        private Long id;
        private String title;
        private String content;
        private Integer views;
        private LocalDateTime createdAt;
        private boolean secret;
        private boolean notification;
        private UserResponseDTO.UserPreviewDTO author;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewResultDTOList {
        private List<PreviewResultDTO> list;
        private int totalPage;
        private int count;
    }
}
