package com.motnip.applicationtracker.controller.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ApplicationRequest(String companyName,
                                 LocalDate applicationDate,
                                 String description,
                                 String note) {
}
