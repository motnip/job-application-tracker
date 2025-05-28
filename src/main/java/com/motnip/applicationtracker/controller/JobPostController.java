package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationProgressUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.controller.response.ApplicationResponse;
import com.motnip.applicationtracker.controller.response.ApplicationNoteResponse;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationStatus;
import com.motnip.applicationtracker.service.ApplicationNoteService;
import com.motnip.applicationtracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class JobPostController {

    private final ApplicationService applicationService;
    private final ApplicationNoteService serviceNote;

    @Autowired
    public JobPostController(ApplicationService applicationService, ApplicationNoteService serviceNote) {
        this.applicationService = applicationService;
        this.serviceNote = serviceNote;
    }

    @PostMapping
    public Application addNewApplication(@RequestBody ApplicationRequest applicationRequest) {
        return applicationService.save(applicationRequest);
    }

    @PatchMapping("/{applicationId}/firstContact-date")
    public Application recordFirstContact(@PathVariable Long applicationId, @RequestBody ApplicationFirstContactRequest firstContact) {
        return applicationService.updateFirstContact(applicationId, firstContact);
    }

    @PatchMapping("/{applicationId}/status")
    public Application updateApplication(@PathVariable Long applicationId, @RequestParam ApplicationStatus newStatus) {
        return applicationService.updateStatus(applicationId, newStatus);
    }

    /**
     * Aggiorno le note e lo stato dell'applicazione.
     * */
    @PatchMapping("/{applicationId}/progress")
    public Application updateApplication(@PathVariable Long applicationId, @RequestBody ApplicationProgressUpdateRequest progressUpdate) {
        return applicationService.updateProgress(applicationId, progressUpdate);
    }

    @GetMapping
    public List<ApplicationResponse> getAllApplication() {
        return applicationService.getAllApplication();
    }

    @GetMapping("/{id}/notes")
    public List<ApplicationNoteResponse> getApplicationNotes(@PathVariable(name = "id") Long applicationId) {
        return serviceNote.getAllNotesByApplicationId(applicationId);
    }
}
