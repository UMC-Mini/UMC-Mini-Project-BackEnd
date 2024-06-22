package com.miniproject.demo.domain.post.service;

import com.miniproject.demo.domain.post.converter.PostConverter;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.repository.PostRepository;
import com.miniproject.demo.global.error.handler.PostHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post createPost(PostRequestDTO.CreatePostDTO dto) {
        Post post = PostConverter.toPost(dto);
        //TODO: 유저 매핑 추가
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));
        post.increaseViews();
        return post;
    }

    @Override
    public List<Post> getPosts(String keyword, int page, int offset) {

        Pageable pageable= PageRequest.of(page - 1, offset,
                Sort.by(
                        Sort.Order.desc("isNotification"),
                        Sort.Order.desc("createdAt")
                )
        );

        Page<Post> postByPage;
        if (keyword == null) {
            postByPage = postRepository.findAll(pageable);
        }
        else {
            postByPage = postRepository.findPostsByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(pageable, keyword, keyword);
        }

        return postByPage.getContent();
    }

    @Override
    @Transactional
    public Post updatePost(Long id, PostRequestDTO.UpdatePostDTO dto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));

        post.updatePost(dto);
        return post;
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);

        }
    }

    @Override
    public boolean isExist(Long id) {
        return postRepository.existsById(id);
    }
}
