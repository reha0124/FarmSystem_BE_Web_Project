package com.example.board.post.dto;

import jakarta.validation.constraints.Size;

public class PostUpdateRequest {

    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;

    private String content;

    // 기본 생성자
    public PostUpdateRequest() {}

    // 생성자
    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter/Setter
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
}
