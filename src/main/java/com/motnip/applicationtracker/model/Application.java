package com.motnip.applicationtracker.model;

import com.motnip.applicationtracker.exception.JobApplicationStateException;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @Builder.Default
    private LocalDate applicationDate = LocalDate.now();
    private String description;
    private LocalDate firstContactDate;
    @Builder.Default
    private ApplicationState state = ApplicationState.WAITING;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    //@JsonManagedReference
    private List<ApplicationNote> notes = new ArrayList<>();
    @Builder.Default
    private Instant creationDate = Instant.now();
    private Instant updateDate;

    public void addNote(String note) {
        notesList.add(ApplicationNote.builder()
                .application(this)
                .text(note).build());
    }

    public void addNote(ApplicationNote note) {
        note.setApplication(this);
        notes.add(note);
    }

    public Application applicationDate(LocalDate applicationDate) {
        Optional.ofNullable(applicationDate).ifPresent(this::setApplicationDate);
        return this;
    }

    public void setState(ApplicationState newState) {
        if (ApplicationStateHandler.validateStateChange(this.state, newState)) {
            this.state = newState;
        } else {
            throw new JobApplicationStateException(this.state.name());
        }
    }
}
