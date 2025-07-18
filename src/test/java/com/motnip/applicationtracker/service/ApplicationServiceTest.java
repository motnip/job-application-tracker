package com.motnip.applicationtracker.service;

import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.dto.ApplicationDTO;
import com.motnip.applicationtracker.dto.ApplicationWithNotesDTO;
import com.motnip.applicationtracker.exception.JobApplicationStateException;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    private final long applicationId = 1;
    @InjectMocks
    private ApplicationService sut;

    @Mock
    private ApplicationRepository repository;
    @Mock
    private ApplicationNoteService applicationNoteService;

    private Application application;

    @Captor
    private ArgumentCaptor<Application> applicationArgumentCaptor;

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
    void testCreateNewApplicationAndNotes() {

        ApplicationRequest request = ApplicationRequest.builder()
                .companyName("Company Name")
                .applicationDate(LocalDate.now())
                .description("Job description and company description")
                .note("Note about this application")
                .build();


        //when
        when(repository.save(applicationArgumentCaptor.capture())).thenAnswer(invocation -> {
            Application result = (Application) invocation.getArgument(0);
            result.setId(applicationId);
            result.setCreationDate(Instant.now());
            result.setUpdateDate(Instant.now());
            return result;
        });

        when(applicationNoteService.getAllNotesByApplicationId(eq(applicationId))).thenReturn(Collections.emptyList());

        ApplicationWithNotesDTO applicationDTO = sut.save(request);

        //then
        Application actualApplication = applicationArgumentCaptor.getValue();
        assertAll("actualApplication",
                () -> assertEquals(1, actualApplication.getId()),
                () -> assertEquals(request.companyName(), actualApplication.getCompanyName()),
                () -> assertEquals(request.applicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(request.description(), actualApplication.getDescription()),
                () -> assertNull(actualApplication.getFirstContactDate()),
                () -> assertEquals(ApplicationState.WAITING, actualApplication.getState()),
                () -> assertEquals(1, actualApplication.getNotes().size())
        );

        assertAll("applicationDto",
                () -> assertEquals(1, applicationDTO.id()),
                () -> assertEquals(request.companyName(), applicationDTO.companyName()),
                () -> assertEquals(request.applicationDate(), applicationDTO.applicationDate()),
                () -> assertEquals(request.description(), applicationDTO.description()),
                () -> assertNull(applicationDTO.firstContactDate()),
                () -> assertEquals(ApplicationState.WAITING, applicationDTO.status()),
                () -> assertEquals(1, actualApplication.getNotes().size()),
                () -> assertEquals(request.note(), actualApplication.getNotes().get(0).getText()),
                () -> assertNotNull(applicationDTO.creationDate()),
                () -> assertNull(applicationDTO.updateDate())
        );

    }

    @Test
    void whenUpdateFirstContactThenUpdateFirstContaCtDateAndState() {

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