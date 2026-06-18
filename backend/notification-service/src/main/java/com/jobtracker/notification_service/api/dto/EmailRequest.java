package com.jobtracker.notification_service.api.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private String email; // Remove

    private Long userId;

    private Long applicationId;

    private String notificationType;

    private String message;

    private LocalDateTime scheduledFor;
}
