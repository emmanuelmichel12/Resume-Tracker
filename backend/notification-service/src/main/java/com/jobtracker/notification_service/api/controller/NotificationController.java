package com.jobtracker.notification_service.api.controller;

import org.springframework.web.bind.annotation.*;

import com.jobtracker.notification_service.api.dto.EmailRequest;
import com.jobtracker.notification_service.api.dto.EmailResponse;
import com.jobtracker.notification_service.service.NotificationService;
import com.resend.core.exception.ResendException;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public EmailResponse emailUser(@RequestBody EmailRequest request) throws ResendException {

        return notificationService.sendNotification(request);
    }

}
