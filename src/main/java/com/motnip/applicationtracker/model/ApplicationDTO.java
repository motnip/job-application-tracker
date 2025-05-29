package com.motnip.applicationtracker.model;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationDTO(
        Long id,
        String companyName,
        LocalDate applicationDate,
        String description, LocalDate firstContactDate,
        ApplicationState status,
        Instant creationDate,
        Instant updateDate
) {
}
