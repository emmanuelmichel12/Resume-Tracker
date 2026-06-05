package com.jobtracker.auth_service.api.dto;

public class ResumeLoginRequest {
    private String username;
    private String password;

    public ResumeLoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
