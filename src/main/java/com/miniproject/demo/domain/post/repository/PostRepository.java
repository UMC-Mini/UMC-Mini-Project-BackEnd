package com.miniproject.demo.domain.post.repository;

import com.miniproject.demo.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            Pageable pageable, String title, String content
    );

    Page<Post> findAll(Pageable pageable);

    int countBy();
}
