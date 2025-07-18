package com.motnip.applicationtracker.model;

import static com.motnip.applicationtracker.model.ApplicationStateHandler.validateStateChange;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ApplicationStateHandlerTest {

    @Test
    public void validateCorrectStateTransactions(){

        assertTrue(validateStateChange(ApplicationState.WAITING, ApplicationState.REJECTED));

    }
}