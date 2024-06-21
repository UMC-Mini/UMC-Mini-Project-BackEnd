package com.miniproject.demo.domain.reply.dto;

import com.miniproject.demo.domain.post.validation.annotation.ExistPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReplyDTO {

        @NotBlank
        private String content;

        private boolean secret;

        @ExistPost
        private Long postId;

        private Long parentId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReplyDTO {
        private String content;
        private Boolean secret;
    }
}
