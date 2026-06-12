package com.jobtracker.ai_service.api.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeHistoryResponse {

    private Long id;
    private Long applicationId;
    private String jobDescription;
    private String aiOutput;
    private LocalDateTime createdAt;
}

// Service will return this as a list List<ResumeHistoryResponse>
// getResumeHistory(Long applicationId)
