package com.jobtracker.auth_service.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobtracker.auth_service.api.dto.ResumeLoginRequest;
import com.jobtracker.auth_service.api.dto.ResumeLoginResponse;
import com.jobtracker.auth_service.api.dto.ResumeRegisterRequest;
import com.jobtracker.auth_service.api.dto.UserResponse;
import com.jobtracker.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ResumeAuthController {

    private final AuthService authService;

    public ResumeAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody ResumeRegisterRequest request) {
        return authService.register(request);
    }

    public ResumeLoginResponse login(@RequestBody ResumeLoginRequest request) {
        return authService.login(request);
    }

}
