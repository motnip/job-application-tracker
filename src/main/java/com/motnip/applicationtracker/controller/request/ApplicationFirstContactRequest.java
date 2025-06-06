package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationState;

import java.time.LocalDate;

public record ApplicationFirstContactRequest(
        LocalDate fistContactDate,
        ApplicationState state){
}
