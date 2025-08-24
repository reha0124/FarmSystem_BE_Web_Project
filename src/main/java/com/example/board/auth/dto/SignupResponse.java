package com.example.board.auth.dto;

public class SignupResponse {
    private final String username;
    private final String status;

    public SignupResponse(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() { return username; }
    public String getStatus() { return status; }
}