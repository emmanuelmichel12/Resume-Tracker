package com.jobtracker.notification_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    public void sendEmail(String to, String subject, String body) {

        RestClient client = RestClient.create();

        String json = """
                {
                    "from": "onboarding@resend.dev",
                    "to": ["%s"],
                    "subject": "%s",
                    "text": "%s"
                }
                """.formatted(to, subject, body);

        client.post()
                .uri("https://api.resend.com/emails")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(json)
                .retrieve()
                .toBodilessEntity();
    }
}
