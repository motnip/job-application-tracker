package com.motnip.applicationtracker.model;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record ApplicationWithNotesDTO(
        Long id,
        String companyName,
        LocalDate applicationDate,
        String description, LocalDate firstContactDate,
        ApplicationState status,
        List<ApplicationNoteDTO> notes,
        Instant creationDate,
        Instant updateDate
) {
}
