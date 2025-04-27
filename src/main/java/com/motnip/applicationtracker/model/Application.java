package com.motnip.applicationtracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private Long id;
    private String companyName;
    @Builder.Default
    private LocalDate applicationDate = LocalDate.now();
    private String description;
    private Instant firstContactDate;
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.WAITING;
    private String notes;
    @Builder.Default
    private Instant creationDate = Instant.now();
    private Instant updateDate;
}
