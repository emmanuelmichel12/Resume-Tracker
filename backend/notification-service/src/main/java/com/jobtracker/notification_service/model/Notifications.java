package com.jobtracker.notification_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications", schema = "notificationservice")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "applicationid")
    private Long applicationId;

    @Column(name = "notificationtype", length = 100)
    private String notificationtype;

    @Column(name = "message")
    private String message;

    @Column(name = "wassent")
    private Boolean wasSent = false;

    @Column(name = "scheduledfor")
    private LocalDateTime scheduledFor;

    @Column(name = "sentat")
    private LocalDateTime sentAt;

    @CreationTimestamp
    @Column(name = "createdat")
    private LocalDateTime createdAt;

}
