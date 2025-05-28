package com.motnip.applicationtracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application_notes")
public class ApplicationNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_id")
    //@JsonBackReference
    private Application application;
    private String text;
    @Builder.Default
    private Instant creationTime = Instant.now();
    private Instant updateTime;
}
