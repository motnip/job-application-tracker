package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationState;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationFirstContactRequest(
        LocalDate fistContactDate,
        ApplicationState state) {
}
