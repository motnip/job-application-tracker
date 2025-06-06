package com.motnip.applicationtracker.controller.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record ApplicationNoteRequest (
        String text
){
}
