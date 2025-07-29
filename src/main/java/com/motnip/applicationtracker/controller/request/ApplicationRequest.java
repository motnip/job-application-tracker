package com.motnip.applicationtracker.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationRequest(
        @NotNull
        String companyName,
        @NotNull
        @PastOrPresent
        LocalDate applicationDate,
        String description,
        String note) {
}
