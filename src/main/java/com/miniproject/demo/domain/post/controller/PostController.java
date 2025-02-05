package com.miniproject.demo.domain.post.controller;

import com.miniproject.demo.domain.post.converter.PostConverter;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.dto.PostResponseDTO;
import com.miniproject.demo.domain.post.service.PostService;
import com.miniproject.demo.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public BaseResponse<PostResponseDTO.JoinResultDTO> createPost(Authentication authentication, @RequestBody @Valid PostRequestDTO.CreatePostDTO request) {
        Post post = postService.createPost(authentication, request);
        return BaseResponse.onSuccess(PostConverter.toJoinResultDTO(post));
    }

    @PostMapping("/posts/get")
    public BaseResponse<PostResponseDTO.PreviewResultDTO> getPost(@RequestBody PostRequestDTO.GetPost request) {
        Post post = postService.getPost(request);
        return BaseResponse.onSuccess(PostConverter.toPreviewResultDTO(post));
    }

    @GetMapping("/posts")
    public BaseResponse<PostResponseDTO.PreviewResultDTOList> getPosts(@RequestParam(required = false, defaultValue = "1") int page,
                                                                         @RequestParam(required = false, defaultValue = "10") Integer offset,
                                                                         @RequestParam(required = false) String search) {
        List<Post> posts = postService.getPosts(search, page, offset);
        int count = postService.countOfPost();
        int totalPage = postService.totalPage(offset);
        return BaseResponse.onSuccess(PostConverter.toPreviewResultDTOList(posts, count, totalPage));
    }

    @PatchMapping("/posts/{postId}")
    public BaseResponse<PostResponseDTO.PreviewResultDTO> updatePost(Authentication authentication,
                                                                     @PathVariable Long postId,
                                                                     @RequestBody PostRequestDTO.UpdatePostDTO request) {
        Post post = postService.updatePost(authentication, postId, request);
        return BaseResponse.onSuccess(PostConverter.toPreviewResultDTO(post));
    }

    @DeleteMapping("/posts/{postId}")
    public BaseResponse<Long> deletePost(Authentication authentication, @PathVariable Long postId) {
        postService.deletePost(authentication, postId);
        return BaseResponse.onSuccess(postId);
    }
}
