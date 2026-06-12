package com.jobtracker.ai_service.api.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiResumeResponse {

    private Long id;
    private Long userId;
    private Long applicationId;
    private String s3Url;
    private String resumeText;
    private String jobDescription;
    private String aiOutput;
    private LocalDateTime createdAt;

}
