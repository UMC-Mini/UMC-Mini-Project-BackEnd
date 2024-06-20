package com.miniproject.demo.domain.post.domain;

import com.miniproject.demo.domain.post.dto.PostRequestDTO;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "views")
    private Integer views;

    @Column(name = "is_secret", nullable = false)
    private boolean isSecret;

    @Column(name = "is_notification", nullable = false)
    private boolean isNotification;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Post(String title, String content, boolean isSecret, boolean isNotification) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
        this.isNotification = isNotification;
        this.views = 0;
    }

    public void updatePost(PostRequestDTO.UpdatePostDTO dto) {
        Optional.ofNullable(dto.getTitle()).ifPresent(value -> this.title = value);
        Optional.ofNullable(dto.getContent()).ifPresent(value -> this.content = value);
        this.isSecret = dto.isSecret();
    }

    public void increaseViews() {
        this.views++;
    }
}