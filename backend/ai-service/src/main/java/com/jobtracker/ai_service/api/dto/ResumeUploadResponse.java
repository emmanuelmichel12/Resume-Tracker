package com.jobtracker.ai_service.api.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUploadResponse {

    private Long id;
    private Long userId;
    private String s3Url;
    private LocalDateTime createdAt;
}

// Information after upload