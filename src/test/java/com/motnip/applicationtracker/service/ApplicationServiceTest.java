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
import static org.mockito.Mockito.*;

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
                .updateDate(Instant.now())
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
                () -> assertEquals(ApplicationState.WAITING, applicationDTO.state()),
                () -> assertEquals(1, actualApplication.getNotes().size()),
                () -> assertEquals(request.note(), actualApplication.getNotes().get(0).getText()),
                () -> assertNotNull(applicationDTO.creationDate()),
                () -> assertNull(applicationDTO.updateDate())
        );

    }

    @Test
    void testUpdateFirstContactDateAndStaus() {
        //given
        var firstContactRequest = ApplicationFirstContactRequest.builder()
                .fistContactDate(LocalDate.now())
                .state(ApplicationState.REJECTED)
                .build();

        //when
        when(repository.findById(eq(applicationId))).thenReturn(Optional.of(application));
        when(repository.save(applicationArgumentCaptor.capture())).thenAnswer(invocation -> {
            Application result = (Application) invocation.getArgument(0);
            result.setId(applicationId);
            result.setCreationDate(Instant.now());
            result.setUpdateDate(Instant.now());
            return result;
        });
        ApplicationDTO result = sut.updateFirstContact(applicationId, firstContactRequest);

        //then
        Application actualApplication = applicationArgumentCaptor.getValue();
        assertAll("actualApplication",
                () -> assertEquals(1, actualApplication.getId()),
                () -> assertEquals(application.getCompanyName(), actualApplication.getCompanyName()),
                () -> assertEquals(application.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(application.getDescription(), actualApplication.getDescription()),
                () -> assertEquals(firstContactRequest.fistContactDate(), actualApplication.getFirstContactDate()),
                () -> assertEquals(firstContactRequest.state(), actualApplication.getState()),
                () -> assertNotNull(actualApplication.getUpdateDate())
        );
        assertDoesNotThrow(() -> JobApplicationStateException.class);
        assertThat(result.state(), is(ApplicationState.REJECTED));
    }

    @Test
    void when_UpdateFirstContactDate_And_NewStateNotValid_thenError() {
        //given
        var firstContactRequest = ApplicationFirstContactRequest.builder()
                .fistContactDate(LocalDate.now())
                .state(ApplicationState.CONFIRMED)
                .build();

        //when
        when(repository.findById(eq(applicationId))).thenReturn(Optional.of(application));

        //then
        var actualException = assertThrows(JobApplicationStateException.class, () -> sut.updateFirstContact(applicationId, firstContactRequest));
        assertThat(actualException.getMessage(), is("No valid transition from starting status: WAITING"));
        verify(repository, never()).save(any(Application.class));
    }

    @Test
    void testUpdateApplicationState() {

        //given
        ApplicationState newState = ApplicationState.IN_PROGRESS;

        //when
        when(repository.findById(eq(applicationId))).thenReturn(Optional.of(application));
        when(repository.save(applicationArgumentCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        ApplicationDTO result = sut.updateState(applicationId, newState);

        //then
        Application actualApplication = applicationArgumentCaptor.getValue();
        assertAll("actualApplication",
                () -> assertEquals(1, actualApplication.getId()),
                () -> assertEquals(application.getCompanyName(), actualApplication.getCompanyName()),
                () -> assertEquals(application.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(application.getDescription(), actualApplication.getDescription()),
                () -> assertEquals(application.getFirstContactDate(), actualApplication.getFirstContactDate()),
                () -> assertEquals(newState, actualApplication.getState()),
                () -> assertNotEquals(application.getApplicationDate(), actualApplication.getUpdateDate())
        );
        assertDoesNotThrow(() -> JobApplicationStateException.class);
        assertThat(result.state(), is(newState));
    }

    @Test
    void when_updateState_toInvalidState_then_throwError() {

        //given
        ApplicationState newState = ApplicationState.EXPIRED;

        application.setState(ApplicationState.REJECTED);

        //when
        when(repository.findById(eq(applicationId))).thenReturn(Optional.of(application));

        var actualException= assertThrows(JobApplicationStateException.class, () -> sut.updateState(applicationId, newState));

        //then
        verify(repository, never()).save(eq(application));
        assertThat(actualException.getMessage(), is("No valid transition from starting status: REJECTED"));
    }
}