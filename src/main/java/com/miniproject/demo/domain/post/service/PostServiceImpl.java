package com.miniproject.demo.domain.post.service;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.account.repository.UserRepository;
import com.miniproject.demo.domain.post.converter.PostConverter;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.repository.PostRepository;
import com.miniproject.demo.global.config.PrincipalDetails;
import com.miniproject.demo.global.error.handler.PostHandler;
import com.miniproject.demo.global.error.handler.UserHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Post createPost(Authentication authentication, PostRequestDTO.CreatePostDTO dto) {
        if ((dto.isSecret() && dto.getPassword() == null) || (!dto.isSecret() && dto.getPassword() != null)) {
            throw new PostHandler(ErrorStatus.POST_SECRET);
        }

        if (authentication == null) {
            throw new UserHandler(ErrorStatus._AUTHENTICATION_FAILED);
        }
        Post post = PostConverter.toPost(dto);

        String email = ((PrincipalDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserHandler(ErrorStatus._NOT_FOUND_USER));
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post getPost(PostRequestDTO.GetPost request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));
        if (post.isSecret()) {
            if (request.getPassword() == null) {
                throw new PostHandler(ErrorStatus.POST_INVALID_PASSWORD);
            }
            else if (!request.getPassword().equals(post.getPassword())) {
                throw new PostHandler(ErrorStatus.POST_INCORRECT_PASSWORD);
            }
        }
        else if (request.getPassword() != null) {
            throw new PostHandler(ErrorStatus.POST_SECRET);
        }

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
    public Post updatePost(Authentication authentication, Long id, PostRequestDTO.UpdatePostDTO dto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));

        if (!((PrincipalDetails) authentication.getPrincipal()).getUserId().equals(post.getUser().getId())) {
            throw new PostHandler(ErrorStatus.POST_NOT_WRITER);
        }
        post.updatePost(dto);
        return post;
    }

    @Override
    @Transactional
    public void deletePost(Authentication authentication, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostHandler(ErrorStatus.REPLY_NOT_FOUND));

        if (!((PrincipalDetails) authentication.getPrincipal()).getUserId().equals(post.getUser().getId())) {
            throw new PostHandler(ErrorStatus.POST_NOT_WRITER);
        }
        postRepository.deleteById(id);
    }

    @Override
    public int totalPage(int offset) {
        return (postRepository.countBy() - 1) / offset + 1;
    }

    @Override
    public int countOfPost() {
        return postRepository.countBy();
    }

    @Override
    public boolean isExist(Long id) {
        return postRepository.existsById(id);
    }
}
