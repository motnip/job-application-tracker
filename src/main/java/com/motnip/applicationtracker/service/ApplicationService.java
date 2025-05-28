package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationProgressUpdateRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.model.*;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    @Autowired
    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public Application save(ApplicationRequest request) {

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
        return repository.save(application);
    }


    public List<Application> getAllApplication() {
        return repository.findAll();
    }

    public Application updateFirstContact(Long applicationId, ApplicationFirstContactRequest updateRequest) {

        var application = getApplicationById(applicationId);
        application.setFirstContactDate(updateRequest.fistContactDate());
        application.setUpdateDate(Instant.now());
        return repository.save(application);
    }

    public Application updateStatus(Long applicationId, ApplicationStatus newStatus) {

        var application = getApplicationById(applicationId);
        application.setStatus(newStatus);
        application.setUpdateDate(Instant.now());
        return repository.save(application);
    }

    public Application updateProgress(Long applicationId, ApplicationProgressUpdateRequest progressUpdate) {

        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "NOT IMPLEMENTED YET");
    }

    private Application getApplicationById(Long applicationId) {
        return repository
                .findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "item not found"));
    }
}
