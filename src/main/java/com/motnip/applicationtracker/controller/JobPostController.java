package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationAdvancementUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/application")
public class JobPostController {

    @Autowired
    private JobApplicationService service;

    @PostMapping
    public Application addNewApplication(@RequestBody ApplicationRequest newApplication) {

        Application.ApplicationBuilder newApplicationBuilder =  Application.builder()
                .companyName(newApplication.companyName())
                .description(newApplication.description())
                .notes(newApplication.notes());
        Optional.ofNullable(newApplication.applicationDate()).ifPresent(newApplicationBuilder::applicationDate);
        return newApplicationBuilder.build();
    }

    @PatchMapping("/firstContact-date")
    public Application recordFirstContact(ApplicationFirstContactRequest firstContact) {
        return service.updateFirstContact(firstContact);
    }

    @PatchMapping("/status")
    public Application updateApplication(ApplicationAdvancementUpdateRequest updateRequest) {
        return service.updateAdvancement(updateRequest);
    }
}
