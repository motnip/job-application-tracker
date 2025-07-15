package com.motnip.applicationtracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class Application {

    private String companyName;
    @Builder.Default
    private Instant creationDate = Instant.now();
    private Instant updateDate;
    private String description;
    private Instant firstContactDate;
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.WAITING;
    private String notes;
}
