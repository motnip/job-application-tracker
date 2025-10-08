package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.controller.response.ApplicationResponse;
import com.motnip.applicationtracker.dto.ApplicationDTO;
import com.motnip.applicationtracker.dto.ApplicationWithNotesDTO;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ApplicationWithNotesDTO addNewApplication(@RequestBody @Validated ApplicationRequest applicationRequest) {
        return applicationService.save(applicationRequest);
    }

    /**
     * Return the application details and the notes
     *
     */
    @GetMapping("/{applicationId}")
    public ApplicationWithNotesDTO getApplicationById(@PathVariable Long applicationId) {
        return applicationService.getById(applicationId);
    }

    @PatchMapping("/{applicationId}/firstContact-date")
    public ApplicationDTO recordFirstContact(@PathVariable Long applicationId, @Validated @RequestBody ApplicationFirstContactRequest firstContactRequest) {
        return applicationService.updateFirstContact(applicationId, firstContactRequest);
    }

    @PatchMapping("/{applicationId}/state")
    public ApplicationDTO updateApplication(@PathVariable Long applicationId, @RequestParam(name = "newstate") ApplicationState newState) {
        return applicationService.updateState(applicationId, newState);
    }


    /**
     * Return all the application details, without notes, matching the searching criteria
     *
     */
    @GetMapping
    public List<ApplicationResponse> getAllApplication(@RequestParam(name = "company_name",required = false ) String companyName,
                                                       @RequestParam(required = false ) List<ApplicationState> states) {
        return applicationService.getAllApplicationByParams(companyName,states).stream()
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
