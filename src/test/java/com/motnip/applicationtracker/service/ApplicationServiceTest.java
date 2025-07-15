package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.dto.ApplicationDTO;
import com.motnip.applicationtracker.exception.JobApplicationStateException;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    private final long applicationId = 1;
    @InjectMocks
    ApplicationService sut;

    @Mock
    ApplicationRepository repository;
    private Application application;

    @BeforeEach
    void setUp() {

        application = Application.builder()
                .id(applicationId)
                .companyName("ACME INC.")
                .applicationDate(LocalDate.now())
                .description("Application description")
                .firstContactDate(LocalDate.now())
                .state(ApplicationState.WAITING)
                .creationDate(Instant.now())
                .build();
    }

    @Test
    void whenUpdateFirstContactThenUpdateFirstContatDateAndState() {

        //given
        ApplicationFirstContactRequest request = new ApplicationFirstContactRequest(LocalDate.now(), ApplicationState.REJECTED);


        //when
        when(repository.findById(applicationId)).thenReturn(Optional.ofNullable(application));
        when(repository.save(application)).thenAnswer(invocation -> {
            Application application = invocation.getArgument(0);
            application.setState(ApplicationState.REJECTED);
            return application;
        });
        ApplicationDTO result = sut.updateFirstContact(applicationId, request);

        //then
        assertDoesNotThrow(() -> JobApplicationStateException.class);
        assertThat(result.state(), is(equals(ApplicationState.REJECTED)));
    }
}