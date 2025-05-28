package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.response.ApplicationNoteResponse;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationNote;
import com.motnip.applicationtracker.repository.ApplicationNoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicationNoteService {

    private final ApplicationNoteRepository repository;

    @Autowired
    public ApplicationNoteService(ApplicationNoteRepository repository) {
        this.repository = repository;
    }

    public List<ApplicationNoteResponse> getAllNotesByApplicationId(Long applicationId) {
        return repository.findAllByApplicationId(applicationId)
                .stream().map(
                        n -> ApplicationNoteResponse.builder()
                                .id(n.getId())
                                .text(n.getText())
                                .creationTime(n.getCreationTime())
                                .updateTime(n.getUpdateTime())
                                .build()
                ).collect(Collectors.toList());
    }

    public ApplicationNoteResponse addNewNote(Application application, String noteText) {
        var note = repository.save(
                ApplicationNote.builder()
                        .application(application)
                        .text(noteText)
                        .build()
        );

        return ApplicationNoteResponse.builder()
                .id(note.getId())
                .text(note.getText())
                .creationTime(note.getCreationTime())
                .updateTime(note.getUpdateTime())
                .build();
    }
}
