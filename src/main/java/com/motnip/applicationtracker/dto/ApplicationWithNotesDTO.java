package com.motnip.applicationtracker.dto;

import com.motnip.applicationtracker.model.ApplicationState;
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
        ApplicationState state,
        List<ApplicationNoteDTO> notes,
        Instant creationDate,
        Instant updateDate
) {
}
