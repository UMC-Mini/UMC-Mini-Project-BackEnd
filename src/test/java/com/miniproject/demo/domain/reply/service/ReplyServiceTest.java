package com.miniproject.demo.domain.reply.service;

import com.miniproject.demo.domain.post.domain.Post;
import com.miniproject.demo.domain.post.repository.PostRepository;
import com.miniproject.demo.domain.reply.converter.ReplyConverter;
import com.miniproject.demo.domain.reply.domain.Reply;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import com.miniproject.demo.domain.reply.dto.ReplyResponseDTO;
import com.miniproject.demo.domain.reply.repository.ReplyRepository;
import com.miniproject.demo.global.error.handler.PostHandler;
import com.miniproject.demo.global.error.handler.ReplyHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostRepository postRepository;

    Post post;

    @BeforeEach
    void init() {
        replyRepository.deleteAll();
        postRepository.deleteAll();
        post = savePosts("title", "content", false, false);
    }

    Post savePosts(String title, String content, boolean isSecret, boolean isNotification) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .isSecret(isSecret)
                .isNotification(isNotification)
                .build();
        return postRepository.save(post);
    }

    Reply saveReply(String content, boolean secret, Long postId, Long parentId) {
        Reply reply = Reply.builder()
                .content(content)
                .isSecret(secret)
                .build();

        Reply parent = null;
        if (parentId != null) {
            parent = replyRepository.findById(parentId).orElseThrow(() ->
                    new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));
        }
        reply.setParent(parent);

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));
        reply.setPost(post);

        return replyRepository.save(reply);
    }

    @Test
    void createReply() {
        //given
        final String content = "content";
        final boolean secret = false;
        ReplyRequestDTO.CreateReplyDTO createReplyDTO1 =
                new ReplyRequestDTO.CreateReplyDTO(content, secret, post.getId(), null);

        //when
        Reply reply = replyService.createReply(createReplyDTO1);

        //then
        assertThat(reply.getContent()).isEqualTo(content);
        assertThat(reply.getPost().getId()).isEqualTo(post.getId());
        assertThat(reply.isSecret()).isEqualTo(secret);
        assertThat(reply.getParent()).isNull();
        assertThat(reply.getId()).isNotNull();
    }

    @Test
    void getReplies() {
        //given
        final String content1 = "content1";
        final String content2 = "content2";
        Reply savedReply1 = saveReply(content1, false, post.getId(), null);
        Reply savedReply2 = saveReply(content2, false, post.getId(), savedReply1.getId());

        //when
        List<Reply> replies = replyService.getReplies();

        //then
        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getId()).isEqualTo(savedReply1.getId());
        assertThat(replies.get(1).getId()).isEqualTo(savedReply2.getId());

    }

    @Test
    void getRepliesWithPost() {
        //given
        final String content1 = "content1";
        final String content2 = "content2";
        final String content3 = "content3";
        final String content4 = "content4";
        final String content5 = "content5";

        Reply savedReply1 = saveReply(content1, false, post.getId(), null);
        Reply savedReply2 = saveReply(content2, false, post.getId(), savedReply1.getId());
        Reply savedReply3 = saveReply(content3, false, post.getId(), null);
        Reply savedReply4 = saveReply(content4, false, post.getId(), savedReply3.getId());
        Reply savedReply5 = saveReply(content5, false, post.getId(), savedReply1.getId());

        //when
        List<Reply> result = replyService.getRepliesWithPost(post.getId());

        //then
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getId()).isEqualTo(savedReply1.getId());
        assertThat(result.get(1).getId()).isEqualTo(savedReply2.getId());
        assertThat(result.get(2).getId()).isEqualTo(savedReply5.getId());
        assertThat(result.get(3).getId()).isEqualTo(savedReply3.getId());
        assertThat(result.get(4).getId()).isEqualTo(savedReply4.getId());

    }

    @Test
    void getReply() {
        //given
        final String content1 = "content1";
        Reply savedReply1 = saveReply(content1, false, post.getId(), null);

        //when
        Reply reply = replyService.getReply(savedReply1.getId());

        //then
        assertThat(reply.getId()).isEqualTo(savedReply1.getId());
        assertThat(reply.getContent()).isEqualTo(savedReply1.getContent());
        assertThat(reply.getPost().getId()).isEqualTo(savedReply1.getPost().getId());
        assertThat(reply.isSecret()).isEqualTo(savedReply1.isSecret());

    }

    @Test
    void deleteReply() {
        //given
        final String content1 = "content1";
        Reply savedReply1 = saveReply(content1, false, post.getId(), null);

        //when
        replyService.deleteReply(savedReply1.getId());

        //then
        assertThat(replyRepository.existsById(savedReply1.getId())).isFalse();
    }

    @Test
    void updateReply() {
        //given
        final String content1 = "content1";
        final String modifiedContent = "modifiedContent";
        final boolean secret = false;
        Reply savedReply1 = saveReply(content1, secret, post.getId(), null);
        ReplyRequestDTO.UpdateReplyDTO dto =
                new ReplyRequestDTO.UpdateReplyDTO(modifiedContent, !secret);

        //when
        Reply reply = replyService.updateReply(savedReply1.getId(), dto);

        //then
        assertThat(reply.getId()).isEqualTo(savedReply1.getId());
        assertThat(reply.getContent()).isEqualTo(modifiedContent);
        assertThat(reply.isSecret()).isEqualTo(!secret);

    }

    @Test
    void isExist() {
        //given
        final String content1 = "content1";
        Reply savedReply1 = saveReply(content1, false, post.getId(), null);

        //when
        boolean result = replyService.isExist(savedReply1.getId());

        //then
        assertThat(result).isTrue();

    }
}