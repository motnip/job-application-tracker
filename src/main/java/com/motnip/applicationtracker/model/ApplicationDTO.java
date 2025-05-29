package com.motnip.applicationtracker.model;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record ApplicationDTO(
        Long id,
        String companyName,
        LocalDate applicationDate,
        String description, LocalDate firstContactDate,
        ApplicationStatus status,
        Instant creationDate,
        Instant updateDate
) {
}
