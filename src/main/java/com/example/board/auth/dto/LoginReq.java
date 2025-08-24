package com.example.board.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginReq {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public LoginReq() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginReq{email='" + email + "', password='" + password + "'}";
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
