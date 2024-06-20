package com.miniproject.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePostDTO {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private boolean secret;

        private boolean notification;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePostDTO {
        private String title;
        private String content;
        private boolean secret;
    }
}
