package com.motnip.applicationtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.service.ApplicationNoteService;
import com.motnip.applicationtracker.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;
    @MockitoBean
    private ApplicationNoteService applicationNoteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Application application;
    private static final Long applicationId = 1L;

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
    void when_firstContactDateIsInTheFuture_then_Return400AndErrorMessage() throws Exception {
        //given
        var firstContactRequest = ApplicationFirstContactRequest.builder()
                .fistContactDate(LocalDate.now().plusDays(2))
                .state(ApplicationState.CONFIRMED)
                .build();

        //then
        verifyNoInteractions(applicationService);
        this.mockMvc.perform(patch("/application/1/firstContact-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstContactRequest)
                        )).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("fistContactDate:date cannot be in the future"));

    }
}