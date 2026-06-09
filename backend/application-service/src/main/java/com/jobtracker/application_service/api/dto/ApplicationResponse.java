package com.jobtracker.application_service.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private Long userId;
    private String jobName;
    private String jobRole;
    private String jobUrl;
    private String jobStatus;
    private LocalDate dateApplied;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
