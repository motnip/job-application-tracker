package com.motnip.applicationtracker.model;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record ApplicationNoteDAO(Long id, String text, Instant creationTime, Instant updateTime) {
}
