package com.jobtracker.ai_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AiService {

    @Value("${ai.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateOutput(String resumeText, String jobDescription) throws Exception {

        if (resumeText == null || resumeText.isBlank()) {
            String resumeError = "No resume text was provided.";
            return resumeError;
        }
        if (jobDescription == null || jobDescription.isBlank()) {
            String jobDescriptionError = "No job description was provided.";
            return jobDescriptionError;
        }

        String prompt = "You are a professional resume writer. Given the following resume and job description, generate 5 tailored bullet points that highlight the candidate's relevant experience and skills for this specific role. Format each bullet point starting with a strong action verb.\n\nRESUME:\n"
                + resumeText + "\n\nJOB DESCRIPTION:\n" + jobDescription
                + "\n\nGenerate 5 tailored resume bullet points:";

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                + apiKey;

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        String aiResponse = restTemplate.postForObject(
                url,
                entity,
                String.class);

        if (aiResponse == null || aiResponse.isBlank()) {
            return "No response from AI service.";
        }

        if (aiResponse.contains("error")) {
            return "Error from AI service: " + aiResponse;
        }

        if (!aiResponse.contains("candidates")) {
            return "Unexpected response format from AI service: ";
        }

        JsonNode root = objectMapper.readTree(aiResponse);
        return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").toString().replace("\"",
                "");
    }

}
