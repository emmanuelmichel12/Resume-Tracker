package com.jobtracker.notification_service.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@Component
public class SQSListener {

    private SQSService sqsService;
    private NotificationService notificationService;

    public SQSListener(SQSService sqsService, NotificationService notificationService) {
        this.sqsService = sqsService;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 30000) // polls every 30 seconds
    public void pollQueue() {
        List<Message> messages = sqsService.receiveMessages();

        for (Message message : messages) {
            try {
                // TODO: parse message body and call notificationService
                System.out.println("Received SQS message: " + message.body());
                sqsService.deleteMessage(message.receiptHandle());
            } catch (Exception e) {
                System.out.println("Failed to process SQS message: " + e.getMessage());
            }
        }
    }

}
