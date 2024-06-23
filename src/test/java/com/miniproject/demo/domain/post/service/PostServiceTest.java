package com.miniproject.demo.domain.post.service;

import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.account.repository.UserRepository;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.repository.PostRepository;
import com.miniproject.demo.global.config.PrincipalDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    User user;

    @BeforeEach
    void init() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("test@email.com")
                .password(encoder.encode("test"))
                .name("string")
                .role("USER")
                .build());
        PrincipalDetails principal = new PrincipalDetails(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities())
        );
    }

    Post savePosts(String title, String content, boolean isNotification) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .isSecret(false)
                .isNotification(isNotification)
                .build();
        post.setUser(user);
        return postRepository.save(post);
    }

    @Test
    void createPost() {
        //given
        final String title = "title";
        final String content = "content";
        final String password = "password";
        final boolean isSecret = true;
        final boolean isNotification = false;
        PostRequestDTO.CreatePostDTO dto = new PostRequestDTO.CreatePostDTO(title, content, password, isSecret, isNotification);

        //when
        Post post = postService.createPost(SecurityContextHolder.getContext().getAuthentication(), dto);

        //then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.isSecret()).isEqualTo(isSecret);
        assertThat(post.isNotification()).isEqualTo(isNotification);
        assertThat(post.getViews()).isZero();
        assertThat(post.getId()).isNotNull();

    }

    @Test
    void getPost() {
        //given
        final String title = "title";
        final String content = "content";
        final boolean isNotification = false;
        Long postId = savePosts(title, content, isNotification).getId();

        //when
        Post post = postService.getPost(new PostRequestDTO.GetPost(postId, null));

        //then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.isSecret()).isFalse();
        assertThat(post.isNotification()).isEqualTo(isNotification);
        assertThat(post.getViews()).isEqualTo(1);
        assertThat(post.getId()).isEqualTo(postId);

    }

    @Test
    void getPosts() {
        //given
        savePosts("title1", "content_re", true);
        savePosts("title2", "content8", false);
        savePosts("title3", "content9", true);
        savePosts("title4", "content6", false);
        savePosts("title_Re", "content7", false);
        savePosts("title6", "content8", false);
        int offset = 5;

        //when
        // pagination만 실행
        List<Post> result1 = postService.getPosts(null, 1, offset);

        // title이랑 content에서 다 찾는 지 확인
        List<Post> result2 = postService.getPosts("6", 1, offset);

        // 대소문자 무시하는지 확인
        List<Post> result3 = postService.getPosts("re", 1, offset);

        //then
        assertThat(result1).hasSize(offset);
        assertThat(result1.get(0).getTitle()).isEqualTo("title3");
        assertThat(result1.get(1).getTitle()).isEqualTo("title1");
        assertThat(result1.get(2).getTitle()).isEqualTo("title6");
        assertThat(result1.get(3).getTitle()).isEqualTo("title_Re");
        assertThat(result1.get(4).getTitle()).isEqualTo("title4");

        assertThat(result2).hasSize(2);
        assertThat(result2.get(0).getTitle()).isEqualTo("title6");
        assertThat(result2.get(1).getTitle()).isEqualTo("title4");

        assertThat(result3).hasSize(2);
        assertThat(result3.get(0).getTitle()).isEqualTo("title1");
        assertThat(result3.get(1).getTitle()).isEqualTo("title_Re");
    }

    @Test
    void updatePost() {
        //given
        final String title = "title";
        final String content = "content";
        final boolean isSecret = true;
        final boolean isNotification = false;
        Long postId = savePosts(title, content, isNotification).getId();
        final String modifiedContent = "content1";
        PostRequestDTO.UpdatePostDTO dto = new PostRequestDTO.UpdatePostDTO(null, modifiedContent, false);

        //when
        Post post = postService.updatePost(SecurityContextHolder.getContext().getAuthentication(), postId, dto);

        //then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(modifiedContent);
        assertThat(post.isSecret()).isEqualTo(false);
        assertThat(post.isNotification()).isEqualTo(isNotification);
        assertThat(post.getViews()).isZero();
        assertThat(post.getId()).isEqualTo(postId);
    }

    @Test
    void deletePost() {
        //given
        final String title = "title";
        final String content = "content";
        final boolean isSecret = true;
        final boolean isNotification = false;
        Long postId = savePosts(title, content, isNotification).getId();

        //when
        postService.deletePost(SecurityContextHolder.getContext().getAuthentication(), postId);

        //then
        assertThat(postRepository.existsById(postId)).isFalse();

    }

    @Test
    void totalPage() {
        //given
        final String title = "title";
        final String content = "content";
        final int count = 10;

        for (int i = 0; i < count; i++) {
            savePosts(title + (i + 1), content + (i + 1),  (i + 1) % 2 == 0);
        }

        //when
        int result1 = postService.totalPage(2);
        int result2 = postService.totalPage(3);
        int result3 = postService.totalPage(10);

        //then
        assertThat(result1).isEqualTo(5);
        assertThat(result2).isEqualTo(4);
        assertThat(result3).isEqualTo(1);

    }

    @Test
    void countOfPage() {
        //given
        final String title = "title";
        final String content = "content";
        final int count = 10;

        for (int i = 0; i < count; i++) {
            savePosts(title + (i + 1), content + (i + 1),  (i + 1) % 2 == 0);
        }

        //when
        int result = postService.countOfPost();

        //then
        assertThat(result).isEqualTo(count);

    }

    @Test
    void isExist() {
        //given
        final String title = "title";
        final String content = "content";
        final boolean isSecret = true;
        final boolean isNotification = false;
        Long postId = savePosts(title, content, isNotification).getId();

        //when
        boolean isExist = postService.isExist(postId);
        boolean notExist = postService.isExist(postId + 1);

        //then
        assertThat(isExist).isTrue();
        assertThat(notExist).isFalse();
    }
}