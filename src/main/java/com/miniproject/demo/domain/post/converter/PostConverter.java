package com.miniproject.demo.domain.post.converter;

import com.miniproject.demo.domain.account.converter.UserConverter;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.dto.PostResponseDTO;

import java.util.List;

public class PostConverter {

    public static Post toPost(PostRequestDTO.CreatePostDTO dto) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .isSecret(dto.isSecret())
                .isNotification(dto.isNotification())
                .build();
    }

    public static PostResponseDTO.JoinResultDTO toJoinResultDTO(Post post) {
        return PostResponseDTO.JoinResultDTO.builder()
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static PostResponseDTO.PreviewResultDTO toPreviewResultDTO(Post post) {
        return PostResponseDTO.PreviewResultDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .createdAt(post.getCreatedAt())
                .secret(post.isSecret())
                .notification(post.isNotification())
                .author(UserConverter.toUserPreviewDTO(post.getUser()))
                .build();
    }

    public static PostResponseDTO.PreviewResultDTOList toPreviewResultDTOList(List<Post> postList, int count, int totalPage) {
        List<PostResponseDTO.PreviewResultDTO> list = postList.stream().map(PostConverter::toPreviewResultDTO).toList();
        return PostResponseDTO.PreviewResultDTOList.builder()
                .list(list)
                .count(count)
                .totalPage(totalPage)
                .build();
    }
}
