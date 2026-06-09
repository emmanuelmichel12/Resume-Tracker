package com.jobtracker.application_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobtracker.application_service.api.dto.ApplicationResponse;
import com.jobtracker.application_service.api.dto.CreateApplicationRequest;
import com.jobtracker.application_service.api.dto.UpdateApplicationRequest;
import com.jobtracker.application_service.model.Applications;
import com.jobtracker.application_service.repository.ApplicationRepository;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public ApplicationResponse createApplication(CreateApplicationRequest request) {

        Applications newApplication = new Applications();
        newApplication.setUserId(request.getUserId());
        newApplication.setJobName(request.getJobName());
        newApplication.setJobRole(request.getJobRole());
        newApplication.setJobUrl(request.getJobUrl());
        newApplication.setJobStatus(request.getJobStatus());
        newApplication.setDateApplied(request.getDateApplied());
        newApplication.setNotes(request.getNotes());

        Applications savedApplication = applicationRepository.save(newApplication);
        ApplicationResponse response = new ApplicationResponse();
        response.setId(savedApplication.getId());
        response.setUserId(savedApplication.getUserId());
        response.setJobName(savedApplication.getJobName());
        response.setJobRole(savedApplication.getJobRole());
        response.setJobUrl(savedApplication.getJobUrl());
        response.setJobStatus(savedApplication.getJobStatus());
        response.setDateApplied(savedApplication.getDateApplied());
        response.setNotes(savedApplication.getNotes());
        response.setCreatedAt(savedApplication.getCreatedAt());
        response.setUpdatedAt(savedApplication.getUpdatedAt());

        return response;
    }

    public ApplicationResponse updateApplication(UpdateApplicationRequest request, long id) {

        Applications existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        existingApplication.setJobName(request.getJobName());
        existingApplication.setJobRole(request.getJobRole());
        existingApplication.setJobUrl(request.getJobUrl());
        existingApplication.setJobStatus(request.getJobStatus());
        existingApplication.setDateApplied(request.getDateApplied());
        existingApplication.setNotes(request.getNotes());

        Applications updatedApplication = applicationRepository.save(existingApplication);
        ApplicationResponse response = new ApplicationResponse();
        response.setId(updatedApplication.getId());
        response.setUserId(updatedApplication.getUserId());
        response.setJobName(updatedApplication.getJobName());
        response.setJobRole(updatedApplication.getJobRole());
        response.setJobUrl(updatedApplication.getJobUrl());
        response.setJobStatus(updatedApplication.getJobStatus());
        response.setDateApplied(updatedApplication.getDateApplied());
        response.setNotes(updatedApplication.getNotes());
        response.setCreatedAt(updatedApplication.getCreatedAt());
        response.setUpdatedAt(updatedApplication.getUpdatedAt());

        return response;
    }

    public void deleteApplication(long id) {
        Applications existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(existingApplication);
    }

    public List<ApplicationResponse> getApplicationsByUserId(long userId) {
        List<Applications> applications = applicationRepository.findByUserId(userId);
        return applications.stream().map(app -> {
            ApplicationResponse response = new ApplicationResponse();
            response.setId(app.getId());
            response.setUserId(app.getUserId());
            response.setJobName(app.getJobName());
            response.setJobRole(app.getJobRole());
            response.setJobUrl(app.getJobUrl());
            response.setJobStatus(app.getJobStatus());
            response.setDateApplied(app.getDateApplied());
            response.setNotes(app.getNotes());
            response.setCreatedAt(app.getCreatedAt());
            response.setUpdatedAt(app.getUpdatedAt());
            return response;
        }).collect(Collectors.toList());
    }
}
