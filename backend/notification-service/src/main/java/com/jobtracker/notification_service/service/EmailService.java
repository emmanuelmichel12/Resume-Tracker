package com.jobtracker.notification_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    public void sendEmail(String to, String subject, String body) {

        RestClient client = RestClient.create();

        client.post()
                .uri("https://api.resend.com/emails")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(Map.of(
                        "from", "onboarding@resend.dev",
                        "to", new String[] { to },
                        "subject", subject,
                        "text", body))
                .retrieve()
                .toBodilessEntity();
    }
}
