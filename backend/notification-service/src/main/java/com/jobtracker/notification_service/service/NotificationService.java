package com.jobtracker.notification_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jobtracker.notification_service.api.dto.EmailRequest;
import com.jobtracker.notification_service.api.dto.EmailResponse;
import com.jobtracker.notification_service.api.dto.UserResponse;
import com.jobtracker.notification_service.model.Notifications;
import com.jobtracker.notification_service.repository.NotificationRepository;
import com.resend.core.exception.ResendException;

@Service
public class NotificationService {

    // Save notification to DB
    // Call EmailService to send
    // Updating wasSent & sentAt
    // G etting notification history by userId

    @Value("${auth.service.url}")
    private String authServiceUrl;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    public NotificationService(EmailService emailService, NotificationRepository notificationRepository) {

        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
    }

    public EmailResponse sendNotification(EmailRequest request) throws ResendException {

        Notifications newNotification = new Notifications();
        newNotification.setUserId(request.getUserId());
        newNotification.setApplicationId(request.getApplicationId());
        newNotification.setNotificationtype(request.getNotificationType());
        newNotification.setMessage(request.getMessage());
        newNotification.setScheduledFor(request.getScheduledFor());

        RestTemplate restTemplate = new RestTemplate();
        String userUrl = authServiceUrl + "/api/auth/users/" + request.getUserId();
        UserResponse user = restTemplate.getForObject(userUrl, UserResponse.class);

        String to = user != null ? user.getEmail() : null;

        if (to == null || to.isEmpty()) {
            throw new RuntimeException("User not found or email is null for userId: " + request.getUserId());
        }

        String subject = request.getNotificationType();
        String message = request.getMessage();
        String body;

        if ("FOLLOW UP".equals(subject)) {
            body = "Hi! This is a reminder to follow up on your job application.\n\n" + message;
        } else if ("INTERVIEW".equals(subject)) {
            body = "Hi! You have an interview coming up. Good luck!\n\n" + message;

        } else if ("DEADLINE".equals(subject)) {
            body = "Hi! You have an application deadline approaching.\n\n" + message;
        } else {
            body = request.getMessage();
        }

        Notifications save = notificationRepository.save(newNotification);
        emailService.sendEmail(to, subject, body);
        save.setWasSent(true);
        save.setSentAt(LocalDateTime.now());
        notificationRepository.save(save);

        EmailResponse response = new EmailResponse();
        response.setUserId(save.getUserId());
        response.setApplicationId(save.getApplicationId());
        response.setNotificationType(save.getNotificationtype());
        response.setMessage(save.getMessage());
        response.setScheduledFor(save.getScheduledFor());
        response.setWasSent(save.getWasSent());
        response.setSentAt(save.getSentAt());
        response.setCreatedAt(save.getCreatedAt());

        return response;
    }

}
