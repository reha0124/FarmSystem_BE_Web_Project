package com.example.board.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentRequest {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("postId")
    private Long postId;
    @JsonProperty("content")
    private String content;

    public CommentRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "CommentRequest{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                '}';
    }
}
