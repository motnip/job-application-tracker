package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.dto.ApplicationDTO;
import com.motnip.applicationtracker.dto.ApplicationWithNotesDTO;
import com.motnip.applicationtracker.exception.JobApplicationStateException;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationNote;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

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
                .description(request.description())
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
                .build();
    }

    public List<Application> getAllApplication() {
        return repository.findAll();
    }

    public ApplicationDTO updateFirstContact(Long applicationId, ApplicationFirstContactRequest updateRequest) {

        var application = getApplicationById(applicationId);
        application.setFirstContactDate(updateRequest.fistContactDate());
        setApplicationNewState(application, updateRequest.state());
        application.setUpdateDate(Instant.now());

        return toApplicationDTO(repository.save(application));
    }

    public ApplicationDTO updateState(Long applicationId, ApplicationState newState) {

        var application = getApplicationById(applicationId);
        setApplicationNewState(application, newState);
        application.setUpdateDate(Instant.now());
        return toApplicationDTO(repository.save(application));
    }

    private void setApplicationNewState(Application application, ApplicationState newState) {
        try {
            application.setState(newState);
        } catch (JobApplicationStateException e) {
            log.error("Application ID [{}] - illegal change from state {} to {}", application.getId(), application.getState(), newState);
            throw e;
        }
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
