package com.motnip.applicationtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motnip.applicationtracker.configuration.CommonTestContainerConfiguration;
import com.motnip.applicationtracker.controller.request.ApplicationFirstContactRequest;
import com.motnip.applicationtracker.controller.request.ApplicationRequest;
import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(CommonTestContainerConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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