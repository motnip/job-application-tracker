package com.motnip.applicationtracker.model;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record ApplicationNoteDTO(Long id, String text, Instant creationTime, Instant updateTime) {
}
