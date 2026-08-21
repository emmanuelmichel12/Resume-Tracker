package com.jobtracker.notification_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jobtracker.notification_service.api.dto.UserResponse;
import com.jobtracker.notification_service.model.Notifications;
import com.jobtracker.notification_service.repository.NotificationRepository;

@Component
@EnableScheduling
public class NoificationScheduler {

    @Value("${auth.service.url}")
    private String authServiceUrl;

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

                String recipientEmail = getUserEmail(notification.getUserId());

                if (recipientEmail == null || recipientEmail.isEmpty()) {
                    System.out.println("Failed to fetch email for userId " + notification.getUserId());
                    continue;
                }

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

    private String getUserEmail(Long userId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = authServiceUrl + "/api/auth/users/" + userId;
            UserResponse user = restTemplate.getForObject(url, UserResponse.class);

            if (user != null) {
                return user.getEmail();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch user email for userId " + userId + ": " + e.getMessage());
            return null;
        }
    }

}
