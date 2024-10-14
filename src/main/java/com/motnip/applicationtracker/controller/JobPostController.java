package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationAdvancementUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class JobPostController {

    @Autowired
    private JobApplicationService service;

    @PostMapping
    public Application addNewApplication(ApplicationRequest newApplication) {

        return Application.builder()
                .companyName(newApplication.companyName())
                .description(newApplication.description())
                .notes(newApplication.notes()).build();
    }

    @PatchMapping
    public Application recordFirstContact(ApplicationFirstContactRequest firstContact) {
        return service.updateFirstContact(firstContact);
    }

    @PatchMapping
    public Application updateApplication(ApplicationAdvancementUpdateRequest updateRequest) {
        return service.updateAdvancement(updateRequest);
    }
}
