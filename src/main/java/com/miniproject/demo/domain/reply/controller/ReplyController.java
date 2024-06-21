package com.miniproject.demo.domain.reply.controller;

import com.miniproject.demo.domain.reply.converter.ReplyConverter;
import com.miniproject.demo.domain.reply.domain.Reply;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import com.miniproject.demo.domain.reply.dto.ReplyResponseDTO;
import com.miniproject.demo.domain.reply.service.ReplyService;
import com.miniproject.demo.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/replies")
    public BaseResponse<ReplyResponseDTO.JoinResultDTO> createReply(@RequestBody @Valid ReplyRequestDTO.CreateReplyDTO dto) {
        Reply reply = replyService.createReply(dto);
        return BaseResponse.onSuccess(ReplyConverter.toJoinResultDTO(reply));
    }

    @GetMapping("/replies/{replyId}")
    public BaseResponse<ReplyResponseDTO.PreviewResultDTO> getReply(@PathVariable Long replyId) {
        Reply reply = replyService.getReply(replyId);
        return BaseResponse.onSuccess(ReplyConverter.toPreviewResultDTO(reply));
    }

    @GetMapping("/replies")
    public BaseResponse<List<ReplyResponseDTO.PreviewResultDTO>> getReplies() {
        List<Reply> replies = replyService.getReplies();
        return BaseResponse.onSuccess(ReplyConverter.toPreviewResultDTOList(replies));
    }

    @GetMapping("/posts/{postId}/replies")
    public BaseResponse<List<ReplyResponseDTO.PreviewResultDTO>> getRepliesWithPost(@PathVariable Long postId) {
        List<Reply> replies = replyService.getRepliesWithPost(postId);
        return BaseResponse.onSuccess(ReplyConverter.toPreviewResultDTOList(replies));
    }

    @DeleteMapping("/replies/{replyId}")
    public BaseResponse<Long> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return BaseResponse.onSuccess(replyId);
    }

    @PatchMapping("/replies/{replyId}")
    public BaseResponse<ReplyResponseDTO.PreviewResultDTO> updateReply(@PathVariable Long replyId,
                                                                       @RequestBody ReplyRequestDTO.UpdateReplyDTO dto) {
        Reply reply = replyService.updateReply(replyId, dto);
        return BaseResponse.onSuccess(ReplyConverter.toPreviewResultDTO(reply));
    }
}