package com.motnip.applicationtracker.dto;

import com.motnip.applicationtracker.model.ApplicationState;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationDTO(
        Long id,
        String companyName,
        LocalDate applicationDate,
        String description, LocalDate firstContactDate,
        ApplicationState state,
        Instant creationDate,
        Instant updateDate
) {
}
