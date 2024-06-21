package com.miniproject.demo.domain.reply.service;

import com.miniproject.demo.domain.post.domain.Post;
import com.miniproject.demo.domain.post.repository.PostRepository;
import com.miniproject.demo.domain.reply.converter.ReplyConverter;
import com.miniproject.demo.domain.reply.domain.Reply;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import com.miniproject.demo.domain.reply.repository.ReplyRepository;
import com.miniproject.demo.global.error.handler.PostHandler;
import com.miniproject.demo.global.error.handler.ReplyHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService{

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository ;

    @Override
    @Transactional
    public Reply createReply(ReplyRequestDTO.CreateReplyDTO dto) {
        Reply reply = ReplyConverter.toReply(dto);

        Reply parent = null;
        if (dto.getParentId() != null) {
            parent = replyRepository.findById(dto.getParentId()).orElseThrow(() ->
                    new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));
        }
        reply.setParent(parent);

        Post post = postRepository.findById(dto.getPostId()).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));
        reply.setPost(post);

        //TODO: 유저 매핑

        return replyRepository.save(reply);
    }

    @Override
    public Reply getReply(Long id) {
        return replyRepository.findById(id).orElseThrow(() ->
                new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));
    }

    @Override
    public List<Reply> getReplies() {
        return replyRepository.findAll();
    }

    @Override
    public List<Reply> getRepliesWithPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostHandler(ErrorStatus.POST_NOT_FOUND));
        List<Reply> parents = replyRepository.findRepliesByPostIsAndParentIsNullOrderByCreatedAtAsc(post);

        List<Reply> result = new ArrayList<>();

        for (Reply parent : parents) {
            result.add(parent);
            result.addAll(replyRepository.findRepliesByPostIsAndParentIsOrderByCreatedAtAsc(post, parent));
        }

        return result;
    }

    @Override
    @Transactional
    public void deleteReply(Long id) {
        if (replyRepository.existsById(id)) {
            replyRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Reply updateReply(Long id, ReplyRequestDTO.UpdateReplyDTO dto) {
        Reply reply = replyRepository.findById(id).orElseThrow(() ->
                new ReplyHandler(ErrorStatus.REPLY_NOT_FOUND));

        reply.updateReply(dto);
        return reply;
    }

    @Override
    public boolean isExist(Long id) {
        return replyRepository.existsById(id);
    }
}
