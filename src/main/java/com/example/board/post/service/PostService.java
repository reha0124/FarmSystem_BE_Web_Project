package com.example.board.post.service;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.PostCreateRequest;
import com.example.board.post.dto.PostResponse;
import com.example.board.post.dto.PostUpdateRequest;
import com.example.board.post.repository.PostRepository;
import com.example.board.user.domain.User;
import com.example.board.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성 (Create)
    @Transactional
    public PostResponse createPost(Long authorId, PostCreateRequest request) {
        User author = userRepository.findById(authorId)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + authorId));

        Post post = new Post(request.getTitle(), request.getContent(), author);
        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    // 모든 게시글 조회 (Read)
    public List<PostResponse> getAllPosts() {
        return postRepository.findByIsDeletedFalse()
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // 게시글 페이징 조회
    public Page<PostResponse> getPostsWithPaging(Pageable pageable) {
        return postRepository.findByIsDeletedFalse(pageable)
                .map(PostResponse::from);
    }

    // 특정 게시글 조회 (Read)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        return PostResponse.from(post);
    }

    // 특정 사용자의 게시글 조회
    public Page<PostResponse> getPostsByAuthor(Long authorId, Pageable pageable) {
        return postRepository.findByAuthorIdAndIsDeletedFalse(authorId, pageable)
                .map(PostResponse::from);
    }

    // 게시글 수정 (Update)
    @Transactional
    public PostResponse updatePost(Long id, Long authorId, PostUpdateRequest request) {
        Post post = postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 작성자 확인
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("게시글 수정 권한이 없습니다");
        }

        // 제목 수정
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            post.updateTitle(request.getTitle());
        }

        // 내용 수정
        if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
            post.updateContent(request.getContent());
        }

        Post updatedPost = postRepository.save(post);
        return PostResponse.from(updatedPost);
    }

    // 게시글 삭제 (Delete - 소프트 삭제)
    @Transactional
    public void deletePost(Long id, Long authorId) {
        Post post = postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 작성자 확인
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다");
        }

        post.delete();
        postRepository.save(post);
    }
}
