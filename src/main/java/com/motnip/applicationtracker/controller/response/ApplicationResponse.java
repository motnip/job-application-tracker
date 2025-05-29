package com.motnip.applicationtracker.controller.response;

import com.motnip.applicationtracker.model.ApplicationState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class ApplicationResponse {

    private Long id;
    private String companyName;
    private String description;
    private LocalDate applicationDate;
    private LocalDate firstContactDate;
    private ApplicationState status;
}
