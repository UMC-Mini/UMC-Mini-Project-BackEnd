package com.miniproject.demo.domain.reply.converter;

import com.miniproject.demo.domain.account.converter.UserConverter;
import com.miniproject.demo.domain.reply.domain.Reply;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import com.miniproject.demo.domain.reply.dto.ReplyResponseDTO;

import java.util.List;

public class ReplyConverter {

    public static Reply toReply(ReplyRequestDTO.CreateReplyDTO dto) {
        return Reply.builder()
                .content(dto.getContent())
                .isSecret(dto.isSecret())
                .build();
    }

    public static ReplyResponseDTO.JoinResultDTO toJoinResultDTO(Reply reply) {
        return ReplyResponseDTO.JoinResultDTO.builder()
                .id(reply.getId())
                .createdAt(reply.getCreatedAt())
                .build();
    }

    public static ReplyResponseDTO.PreviewResultDTO toPreviewResultDTO(Reply reply) {
        return ReplyResponseDTO.PreviewResultDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .secret(reply.isSecret())
                .parentId(reply.getParent() == null ? null : reply.getParent().getId())
                .author(UserConverter.toUserPreviewDTO(reply.getUser()))
                .createdAt(reply.getCreatedAt())
                .build();
    }

    public static List<ReplyResponseDTO.PreviewResultDTO> toPreviewResultDTOList(List<Reply> replies) {
        return replies.stream().map(ReplyConverter::toPreviewResultDTO).toList();
    }
}
