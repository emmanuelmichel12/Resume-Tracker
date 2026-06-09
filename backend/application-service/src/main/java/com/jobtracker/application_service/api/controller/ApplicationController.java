package com.jobtracker.application_service.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.jobtracker.application_service.api.dto.ApplicationResponse;
import com.jobtracker.application_service.api.dto.CreateApplicationRequest;
import com.jobtracker.application_service.api.dto.UpdateApplicationRequest;
import com.jobtracker.application_service.service.ApplicationService;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    public ApplicationResponse createApplication(@RequestBody CreateApplicationRequest request) {
        return applicationService.createApplication(request);
    }

    @GetMapping("/{id}")
    public List<ApplicationResponse> getApplicationById(@PathVariable long id) {
        return applicationService.getApplicationsByUserId(id);
    }

    @PutMapping("/update/{id}")
    public ApplicationResponse updateApplication(@RequestBody UpdateApplicationRequest request, @PathVariable long id) {
        return applicationService.updateApplication(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteApplication(@PathVariable long id) {
        applicationService.deleteApplication(id);
    }
}
