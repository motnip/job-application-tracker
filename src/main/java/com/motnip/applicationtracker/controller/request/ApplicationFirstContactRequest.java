package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationFirstContactRequest(
        LocalDate fistContactDate,
        String notes,
        ApplicationStatus newStatus){
}
