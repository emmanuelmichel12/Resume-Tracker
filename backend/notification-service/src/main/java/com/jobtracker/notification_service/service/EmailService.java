package com.jobtracker.notification_service.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    public void sendEmail(String to, String subject, String body) throws ResendException {
        Resend resend = new Resend(apiKey);

        CreateEmailOptions request = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(to)
                .subject(subject)
                .html("<p>" + body + "</p>")
                .build();

        resend.emails().send(request);
    }
}
