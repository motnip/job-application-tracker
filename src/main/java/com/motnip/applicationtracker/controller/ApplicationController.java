package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.controller.response.ApplicationResponse;
import com.motnip.applicationtracker.dto.ApplicationDTO;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.dto.ApplicationWithNotesDTO;
import com.motnip.applicationtracker.service.ApplicationNoteService;
import com.motnip.applicationtracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, ApplicationNoteService serviceNote) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ApplicationWithNotesDTO addNewApplication(@RequestBody ApplicationRequest applicationRequest) {
        return applicationService.save(applicationRequest);
    }

    @PatchMapping("/{applicationId}/firstContact-date")
    public ApplicationDTO recordFirstContact(@PathVariable Long applicationId, @RequestBody ApplicationFirstContactRequest firstContactRequest) {
        return applicationService.updateFirstContact(applicationId, firstContactRequest);
    }

    @PatchMapping("/{applicationId}/state")
    public ApplicationDTO updateApplication(@PathVariable Long applicationId, @RequestParam(name = "newstate") ApplicationState newState) {
        return applicationService.updateState(applicationId, newState);
    }

    @GetMapping
    public List<ApplicationResponse> getAllApplication() {
        return applicationService.getAllApplication().stream()
                .map(application -> ApplicationResponse.builder()
                        .id(application.getId())
                        .companyName(application.getCompanyName())
                        .description(application.getDescription())
                        .applicationDate(application.getApplicationDate())
                        .firstContactDate(application.getFirstContactDate())
                        .status(application.getState())
                        .build())
                .toList();
    }
}
