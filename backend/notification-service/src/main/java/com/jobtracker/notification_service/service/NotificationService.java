package com.jobtracker.notification_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.jobtracker.notification_service.api.dto.EmailRequest;
import com.jobtracker.notification_service.api.dto.EmailResponse;
import com.jobtracker.notification_service.model.Notifications;
import com.jobtracker.notification_service.repository.NotificationRepository;

@Service
public class NotificationService {

    // Save notification to DB
    // Call EmailService to send
    // Updating wasSent & sentAt
    // G etting notification history by userId

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    public NotificationService(EmailService emailService, NotificationRepository notificationRepository) {

        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
    }

    public EmailResponse sendNotification(EmailRequest request) {

        Notifications newNotification = new Notifications();
        newNotification.setUserId(request.getUserId());
        newNotification.setApplicationId(request.getApplicationId());
        newNotification.setNotificationtype(request.getNotificationType());
        newNotification.setMessage(request.getMessage());
        newNotification.setScheduledFor(request.getScheduledFor());

        String to = request.getEmail();
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
