package com.motnip.applicationtracker.controller.request;

import java.time.LocalDate;

public record ApplicationRequest(String companyName,
                                 LocalDate applicationDate,
                                 String description,
                                 String note) {
}
