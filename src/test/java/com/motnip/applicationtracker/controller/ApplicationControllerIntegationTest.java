package com.motnip.applicationtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import com.motnip.applicationtracker.service.ApplicationNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationController sut;

    @MockitoBean
    private ApplicationRepository applicationRepository;
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
    void testCreateNewApplicationSuccessfully() throws Exception {

        //given
        ApplicationRequest request = ApplicationRequest.builder()
                .companyName("ACME INC.")
                .applicationDate(LocalDate.now())
                .build();

        //when
        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(application);
        when(applicationNoteService.getAllNotesByApplicationId(eq(application.getId()))).thenReturn(Collections.emptyList());

        //then
        mockMvc.perform(post("/application")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
        //TODO check the body response

    }

    @Test
    void when_firstContactDateIsInTheFuture_then_Return400AndErrorMessage() throws Exception {
        //given
        var firstContactRequest = ApplicationFirstContactRequest.builder()
                .fistContactDate(LocalDate.now().plusDays(2))
                .state(ApplicationState.CONFIRMED)
                .build();

        //then
        this.mockMvc.perform(patch("/application/1/firstContact-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstContactRequest)
                        )).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("fistContactDate:date cannot be in the future"));
    }
}