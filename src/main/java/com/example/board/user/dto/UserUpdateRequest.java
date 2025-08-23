package com.example.board.user.dto;

import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

    @Size(max = 50, message = "이름은 50자 이하여야 합니다")
    private String name;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
    private String password;

    // 기본 생성자
    public UserUpdateRequest() {}

    // 생성자
    public UserUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
