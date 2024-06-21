package com.miniproject.demo.domain.reply.repository;

import com.miniproject.demo.domain.post.domain.Post;
import com.miniproject.demo.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findRepliesByPostIsAndParentIsNullOrderByCreatedAtAsc(Post post);

    List<Reply> findRepliesByPostIsAndParentIsOrderByCreatedAtAsc(Post post, Reply parent);
}
