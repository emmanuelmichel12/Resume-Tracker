package com.jobtracker.notification_service.api.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {

    private Long id;

    private Long userId;

    private Long applicationId;

    private String notificationType;

    private String message;

    private Boolean wasSent;

    private LocalDateTime scheduledFor;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

}
