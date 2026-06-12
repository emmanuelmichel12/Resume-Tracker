package com.jobtracker.ai_service.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiResumeRequest {

    private Long id;
    private Long applicationId;
    private String jobDescription;
}
