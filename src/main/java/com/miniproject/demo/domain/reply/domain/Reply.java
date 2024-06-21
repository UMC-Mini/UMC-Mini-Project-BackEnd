package com.miniproject.demo.domain.reply.domain;

import com.miniproject.demo.domain.post.domain.Post;
import com.miniproject.demo.domain.reply.dto.ReplyRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(name = "is_secret")
    private boolean isSecret;

    //TODO: 유저 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Reply parent;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Reply(String content, boolean isSecret) {
        this.content = content;
        this.isSecret = isSecret;
    }

    public void updateReply(ReplyRequestDTO.UpdateReplyDTO dto) {
        Optional.ofNullable(dto.getContent()).ifPresent(value -> this.content = value);
        Optional.ofNullable(dto.getSecret()).ifPresent(value -> this.isSecret = value);
    }

    public void setParent(Reply parent) {
        this.parent = parent;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
