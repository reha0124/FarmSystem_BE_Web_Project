// src/main/java/com/example/board/auth/dto/SignupReq.java
package com.example.board.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupReq {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 4)
    private String password;

    @NotBlank
    private String name;

    public SignupReq() {}  // 기본 생성자 필수

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
}
