package com.example.board.post.dto;

import com.example.board.post.domain.Post;
import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private AuthorInfo author;
    private Integer likes;
    private Integer comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public PostResponse() {}

    // 생성자
    public PostResponse(Long id, String title, String content, AuthorInfo author,
                       Integer likes, Integer comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Post 엔티티로부터 생성하는 정적 메서드
    public static PostResponse from(Post post) {
        AuthorInfo authorInfo = new AuthorInfo(
            post.getAuthor().getId(),
            post.getAuthor().getName()
        );

        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            authorInfo,
            post.getLikes(),
            post.getComments(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }

    // Getter/Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorInfo getAuthor() {
        return author;
    }

    public void setAuthor(AuthorInfo author) {
        this.author = author;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 작성자 정보 클래스
    public static class AuthorInfo {
        private Long id;
        private String name;

        public AuthorInfo() {}

        public AuthorInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
