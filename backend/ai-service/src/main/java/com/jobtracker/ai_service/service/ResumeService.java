package com.jobtracker.ai_service.service;

import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.jobtracker.ai_service.api.dto.AiResumeRequest;
import com.jobtracker.ai_service.api.dto.AiResumeResponse;
import com.jobtracker.ai_service.api.dto.ResumeHistoryResponse;
import com.jobtracker.ai_service.api.dto.ResumeUploadResponse;
import com.jobtracker.ai_service.model.Resumes;
import com.jobtracker.ai_service.repository.ResumeRepository;

@Service
public class ResumeService {

    private final AiService aiService;
    private final ResumeRepository resumesRepository;

    public ResumeService(AiService aiService, ResumeRepository resumesRepository) {
        this.aiService = aiService;
        this.resumesRepository = resumesRepository;
    }

    public List<ResumeHistoryResponse> getResumeHistory(Long userId) {
        // Fetch all resumes for the user from the database
        List<Resumes> resumes = resumesRepository.findByUserId(userId);

        // Map to DTOs
        return resumes.stream()
                .map(resume -> new ResumeHistoryResponse(
                        resume.getId(),
                        resume.getApplicationId(),
                        resume.getJobDescription(),
                        resume.getAiOutput(),
                        resume.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public ResumeUploadResponse uploadResume(MultipartFile file, Long userId) throws Exception {

        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfText = pdfStripper.getText(document);
        document.close();

        Resumes resume = new Resumes();
        resume.setUserId(userId);
        resume.setS3Url("local"); // Placeholder
        resume.setResumeText(pdfText);

        Resumes savedResume = resumesRepository.save(resume);
        return new ResumeUploadResponse(
                savedResume.getId(),
                savedResume.getUserId(),
                savedResume.getS3Url(),
                savedResume.getCreatedAt());
    }

    public AiResumeResponse tailorResume(AiResumeRequest request) throws Exception {

        Resumes resume = resumesRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        String resumeText = resume.getResumeText();
        String jobDescription = request.getJobDescription();

        String aiOutput = aiService.generateOutput(resumeText, jobDescription);

        Resumes updatedResume = resume;
        updatedResume.setJobDescription(jobDescription);
        updatedResume.setAiOutput(aiOutput);

        Resumes savedResume = resumesRepository.save(updatedResume);

        return new AiResumeResponse(
                savedResume.getId(),
                savedResume.getUserId(),
                savedResume.getApplicationId(),
                savedResume.getS3Url(),
                savedResume.getResumeText(),
                savedResume.getJobDescription(),
                savedResume.getAiOutput(),
                savedResume.getCreatedAt());
    }

}
