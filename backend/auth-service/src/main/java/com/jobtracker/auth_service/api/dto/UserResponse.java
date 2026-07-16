package com.jobtracker.auth_service.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDateTime createdAt;

}
