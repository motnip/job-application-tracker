package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationStatus;

public record ApplicationAdvancementUpdateRequest(
        String notes,
        ApplicationStatus newStatus){
}
