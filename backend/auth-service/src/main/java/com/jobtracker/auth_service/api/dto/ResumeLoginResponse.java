package com.jobtracker.auth_service.api.dto;

public class ResumeLoginResponse {

    private String token;
    private UserResponse user;

    public ResumeLoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResponse getUser() {
        return user;
    }
}
