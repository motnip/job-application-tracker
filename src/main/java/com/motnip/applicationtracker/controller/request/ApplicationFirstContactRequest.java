package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationFirstContactRequest(

        @PastOrPresent(message = "date cannot be in the future")
        LocalDate fistContactDate,
        @NotNull(message = "jpb application state cannot be null")
        ApplicationState state) {
}
