package com.example.board.post.controller;

import com.example.board.common.response.ApiResponse;
import com.example.board.common.response.PageResponse;
import com.example.board.post.dto.PostCreateRequest;
import com.example.board.post.dto.PostResponse;
import com.example.board.post.dto.PostUpdateRequest;
import com.example.board.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crud/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성 (Create) - POST /api/v1/crud/posts
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestParam Long authorId,  // 임시로 파라미터로 받음 (나중에 JWT에서 추출)
            @Valid @RequestBody PostCreateRequest request) {
        try {
            PostResponse response = postService.createPost(authorId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("INVALID_REQUEST", e.getMessage()));
        }
    }

    // 게시글 목록 조회 (페이징) - GET /api/v1/crud/posts
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortBy = sortParams[0];
        Sort.Direction direction = (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PostResponse> postPage = postService.getPostsWithPaging(pageable);
        PageResponse<PostResponse> pageResponse = PageResponse.from(postPage);
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }

    // 특정 게시글 조회 (Read) - GET /api/v1/crud/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id) {
        try {
            PostResponse response = postService.getPostById(id);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("POST_NOT_FOUND", e.getMessage()));
        }
    }

    // 특정 사용자의 게시글 조�� - GET /api/v1/crud/posts/author/{authorId}
    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPostsByAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponse> postPage = postService.getPostsByAuthor(authorId, pageable);
        PageResponse<PostResponse> pageResponse = PageResponse.from(postPage);

        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }

    // 게시글 수정 (Update) - PUT /api/v1/crud/posts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @RequestParam Long authorId,  // 임시로 파라미터로 받음 (나중에 JWT에서 추출)
            @Valid @RequestBody PostUpdateRequest request) {
        try {
            PostResponse response = postService.updatePost(id, authorId, request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("ACCESS_DENIED", e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("POST_NOT_FOUND", e.getMessage()));
        }
    }

    // 게시글 삭제 (Delete) - DELETE /api/v1/crud/posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id,
            @RequestParam Long authorId) {  // 임시로 파라미터로 받음 (나중에 JWT에서 추출)
        try {
            postService.deletePost(id, authorId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("ACCESS_DENIED", e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("POST_NOT_FOUND", e.getMessage()));
        }
    }
}
