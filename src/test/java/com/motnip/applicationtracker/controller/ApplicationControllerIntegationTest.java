package com.motnip.applicationtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motnip.applicationtracker.configuration.AbstractIntegrationTest;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ApplicationController sut;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testCreateNewApplicationSuccessfully() throws Exception {

        //given
        ApplicationRequest request = ApplicationRequest.builder()
                .companyName("ACME INC.")
                .applicationDate(LocalDate.now())
                .build();

        //then
        mockMvc.perform(post("/application")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        assertThat(applicationRepository.findAll(), hasSize(1));
        //TODO check the body response

    }

    @Test
    void testGetApplicationByIdSuccessfully() throws Exception {

        //then
        mockMvc.perform(get("/application/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                //TOMAS to improve
                .andExpect(jsonPath("$.notes").isArray())
                .andExpect(jsonPath("$.id").value(1));

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