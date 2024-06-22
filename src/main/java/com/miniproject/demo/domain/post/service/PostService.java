package com.miniproject.demo.domain.post.service;

import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {
    /**
     * 게시글을 생성하는 메소드
     * @param dto 생성 요청
     * @return 생성이 완료된 게시글
     */
    Post createPost(Authentication authentication, PostRequestDTO.CreatePostDTO dto);

    /**
     * 게시글 하나를 가져오는 메소드
     * @param id 찾을 게시글의 id
     * @return 찾은 게시글 하나
     */
    Post getPost(Long id);

    /**
     * 게시글 여러개 가져오는 메소드, Keyword로 검색 및 페이지네이션 포함
     * @param keyword 검색할 키워드
     * @param page 현재 페이지 수
     * @param offset 현재 페이지에 들어가는 게시글의 크기
     * @return Paginiation이 완료된 게시글들
     */
    List<Post> getPosts(String keyword, int page, int offset);

    /**
     * 게시글 수정 메소드
     * @param id 수정할 게시글의 id
     * @param dto 수정할 게시글의 내용
     * @return 수정이 완료된 게시글
     */
    Post updatePost(Long id, PostRequestDTO.UpdatePostDTO dto);

    /**
     * 게시글 삭제 메소드
     * @param id 삭제할 게시글의 id
     */
    void deletePost(Long id);

    /**
     * 총 페이지 개수 반환
     * @param offset 페이지 오프셋
     * @return 총 페이지 개수
     */
    int totalPage(int offset);

    /**
     * 게시글의 총 개수 세는 메소드
     * @return DB의 게시글 총 개수
     */
    int countOfPost();

    /**
     * 게시글이 존재하는 지 확인
     * @param id 확인하려는 게시글의 id
     * @return 게시글이 존재하면 true, 존재하지 않으면 false
     */
    boolean isExist(Long id);
}
