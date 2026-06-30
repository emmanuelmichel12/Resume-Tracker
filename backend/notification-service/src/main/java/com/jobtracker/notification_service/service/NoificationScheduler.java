package com.jobtracker.notification_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;

import com.jobtracker.notification_service.model.Notifications;
import com.jobtracker.notification_service.repository.NotificationRepository;

@Component
@EnableScheduling
public class NoificationScheduler {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public NoificationScheduler(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendScheduledNotifications() {
        List<Notifications> pending = notificationRepository
                .findByWasSentFalseAndScheduledForBefore(LocalDateTime.now());

        for (Notifications notification : pending) {
            try {
                // TODO: fetch recipient email from auth-service via API Gateway
                String recipientEmail = ""; // will be populated after API Gateway setup

                emailService.sendEmail(
                        recipientEmail,
                        notification.getNotificationtype(),
                        notification.getMessage());
                notification.setWasSent(true);
                notification.setSentAt(LocalDateTime.now());
                notificationRepository.save(notification);
            } catch (Exception e) {
                System.out.println("Failed to send notification " + notification.getId() + ": " + e.getMessage());
            }
        }
    }

}
