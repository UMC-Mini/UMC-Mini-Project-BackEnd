package com.miniproject.demo.domain.reply.dto;

import com.miniproject.demo.domain.account.dto.UserResponseDTO;
import com.miniproject.demo.domain.account.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyResponseDTO {

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
        private String content;
        private UserResponseDTO.UserPreviewDTO author;
        private boolean secret;
        private Long parentId;
        private LocalDateTime createdAt;
        private List<PreviewResultDTO> children;

        public void setChildren(List<PreviewResultDTO> children) {
            this.children = children;
        }
    }

}
