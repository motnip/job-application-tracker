package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationAdvancementUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.model.Application;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationService {

    private Application inMemoryApplication;

    public JobApplicationService() {
        inMemoryApplication = Application.builder()
                .companyName("ACME INC.")
                .description("www.acmeinc.com/career/9832576")
                .build();
    }

    public Application updateFirstContact(ApplicationFirstContactRequest updateRequest) {

        inMemoryApplication.setFirstContactDate(updateRequest.fistContactDate());
        inMemoryApplication.setStatus(updateRequest.newStatus());
        inMemoryApplication.setNotes(updateRequest.notes());

        return inMemoryApplication;
    }

    public Application updateAdvancement(ApplicationAdvancementUpdateRequest request) {

        inMemoryApplication.setStatus(request.newStatus());
        inMemoryApplication.setNotes(request.notes());

        return inMemoryApplication;
    }
}
