package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationStatus;

public record ApplicationProgressUpdateRequest(
        String notes,
        ApplicationStatus newStatus){
}
