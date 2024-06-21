package com.miniproject.demo.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
        //TODO: Reply, user 추가
    }
}
