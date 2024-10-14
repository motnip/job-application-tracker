package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationStatus;

import java.time.Instant;

public record ApplicationFirstContactRequest(
        Instant fistContactDate,
        String notes,
        ApplicationStatus newStatus){
}
