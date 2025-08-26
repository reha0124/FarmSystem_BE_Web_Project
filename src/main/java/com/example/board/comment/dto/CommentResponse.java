package com.example.board.comment.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, String authorName, String content, LocalDateTime createdAt) {
        this.id = id;
        this.authorName = authorName;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getAuthorName() { return authorName; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

