package com.jobtracker.ai_service.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobtracker.ai_service.api.dto.AiResumeRequest;
import com.jobtracker.ai_service.api.dto.AiResumeResponse;
import com.jobtracker.ai_service.api.dto.ResumeHistoryResponse;
import com.jobtracker.ai_service.api.dto.ResumeUploadResponse;
import com.jobtracker.ai_service.service.ResumeService;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/history/{id}")
    public List<ResumeHistoryResponse> getResumeHistory(@PathVariable Long id) {
        return resumeService.getResumeHistory(id);
    }

    @PostMapping("/ai-tailoring")
    public AiResumeResponse tailorResume(@RequestBody AiResumeRequest request) throws Exception {
        return resumeService.tailorResume(request);
    }

    @PostMapping("/upload")
    public ResumeUploadResponse uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws Exception {
        return resumeService.uploadResume(file, userId);
    }
}
