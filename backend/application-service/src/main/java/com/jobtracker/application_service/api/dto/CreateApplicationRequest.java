package com.jobtracker.application_service.api.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationRequest {

    private Long userId;
    private String jobName;
    private String jobRole;
    private String jobUrl;
    private String jobStatus;
    private LocalDate dateApplied;
    private String notes;

}
