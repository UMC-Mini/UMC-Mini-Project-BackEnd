package com.miniproject.demo.domain.reply.service;

import com.miniproject.demo.domain.reply.domain.Reply;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글 생성
     * @param dto 생성 요청 dto
     * @return 생성된 Reply
     */
    Reply createReply(Authentication authentication, ReplyRequestDTO.CreateReplyDTO dto);

    /**
     * 모든 댓글 반환
     * @return 찾은 댓글들 반환
     */
    List<Reply> getReplies();

    /**
     * 특정 게시글의 모든 댓글과 대댓글을 시간순으로 반환
     * @param postId 찾을 댓글들의 게시글 id
     * @return 찾은 댓글들을 정렬하여 반환
     */
    List<Reply> getRepliesWithPost(Long postId);

    /**
     * 댓글 하나 찾기
     * @param id 찾을 댓글의 id
     * @return 찾은 댓글
     */
    Reply getReply(Long id);

    /**
     * 댓글 삭제 메소드
     * @param id 삭제할 댓글의 id
     */
    void deleteReply(Long id);

    /**
     * 댓글 수정하기
     * @param id 수정할 댓글의 id
     * @param dto 댓글 수정 요청 dto
     * @return 수정된 댓글
     */
    Reply updateReply(Long id, ReplyRequestDTO.UpdateReplyDTO dto);

    /**
     * 댓글이 존재하는 지 확인하기
     * @param id 확인하고 싶은 댓글의 id
     * @return 존재 여부, true이면 존재하고 false면 존재하지 않음
     */
    boolean isExist(Long id);
}
