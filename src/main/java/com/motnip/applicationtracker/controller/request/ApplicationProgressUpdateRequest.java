package com.motnip.applicationtracker.controller.request;

import com.motnip.applicationtracker.model.ApplicationState;

public record ApplicationProgressUpdateRequest(
        String note,
        ApplicationState state){
}
