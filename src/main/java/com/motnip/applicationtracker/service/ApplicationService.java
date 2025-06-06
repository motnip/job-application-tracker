package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationProgressUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.model.*;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final ApplicationNoteService applicationNoteService;

    @Autowired
    public ApplicationService(ApplicationRepository repository, ApplicationNoteService applicationNoteService) {
        this.repository = repository;
        this.applicationNoteService = applicationNoteService;
    }

    public ApplicationWithNotesDTO save(ApplicationRequest request) {

        log.debug("Salvo application");

        var application = Application.builder()
                .companyName(request.companyName())
                .applicationDate(request.applicationDate())
                .build();
        application.addNote(ApplicationNote.builder()
                .text(request.note())
                .creationTime(Instant.now())
                .updateTime(Instant.now())
                .build());
        var newApplication = repository.save(application);

        return ApplicationWithNotesDTO.builder()
                .id(newApplication.getId())
                .companyName(newApplication.getCompanyName())
                .applicationDate(newApplication.getApplicationDate())
                .description(newApplication.getDescription())
                .firstContactDate(newApplication.getFirstContactDate())
                .notes(applicationNoteService.getAllNotesByApplicationId(newApplication.getId()))
                .status(newApplication.getState())
                .creationDate(newApplication.getCreationDate())
                .updateDate(newApplication.getUpdateDate())
                .build();

    }

    public List<Application> getAllApplication() {
        return repository.findAll();
    }

    public ApplicationDTO updateFirstContact(Long applicationId, ApplicationFirstContactRequest updateRequest) {

        var application = getApplicationById(applicationId);
        application.setFirstContactDate(updateRequest.fistContactDate());
        try {
            application.setState(updateRequest.status());
        } catch (IllegalStateException e) {
            //TODO use global exception handler
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status change not allowed: " + e.getMessage());
        }
        application.setUpdateDate(Instant.now());

        applicationNoteService.addNewNote(application, updateRequest.note());

        return toApplicationDTO(repository.save(application));
    }

    public ApplicationDTO updateState(Long applicationId, ApplicationState newState) {

        var application = getApplicationById(applicationId);
        application.setState(newState);
        application.setUpdateDate(Instant.now());
        return toApplicationDTO(repository.save(application));
    }

    public ApplicationDTO updateProgress(Long applicationId, ApplicationProgressUpdateRequest progressUpdate) {
        var result = updateState(applicationId, progressUpdate.state());
        applicationNoteService.addNewNote(
                getApplicationById(applicationId),progressUpdate.note());
        return result;
    }

    public Application getApplicationById(Long applicationId) {
        return repository
                .findById(applicationId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "item not found"));
    }

    private ApplicationDTO toApplicationDTO(Application application) {
        return ApplicationDTO.builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .applicationDate(application.getApplicationDate())
                .description(application.getDescription())
                .firstContactDate(application.getFirstContactDate())
                .state(application.getState())
                .creationDate(application.getCreationDate())
                .updateDate(application.getUpdateDate())
                .build();
    }
}
