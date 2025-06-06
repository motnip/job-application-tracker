package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.controller.request.ApplicationNoteRequest;
import com.motnip.applicationtracker.model.ApplicationNoteDTO;
import com.motnip.applicationtracker.service.ApplicationNoteService;
import com.motnip.applicationtracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationNoteController {

    private final ApplicationService applicationService;
    private final ApplicationNoteService serviceNote;

    @Autowired
    public ApplicationNoteController(ApplicationService applicationService, ApplicationNoteService serviceNote) {
        this.applicationService = applicationService;
        this.serviceNote = serviceNote;
    }

    @PostMapping("/{id}/notes")
    public ApplicationNoteDTO addApplicationNotes(@PathVariable(name = "id") Long applicationId, @RequestBody ApplicationNoteRequest applicationNoteRequest) {
        return serviceNote.addNewNote(applicationService.getApplicationById(applicationId),
                applicationNoteRequest.text()
        );
    }


    @GetMapping("/{id}/notes")
    public List<ApplicationNoteDTO> getApplicationNotes(@PathVariable(name = "id") Long applicationId) {
        return serviceNote.getAllNotesByApplicationId(applicationId);
    }
}
