package com.motnip.applicationtracker.controller.response;


import lombok.Builder;

import java.time.Instant;


public record ApplicationNoteResponse(Long id,
                                      String text,
                                      Instant creationTime,
                                      Instant updateTime) {
    @Builder public ApplicationNoteResponse {}
}
