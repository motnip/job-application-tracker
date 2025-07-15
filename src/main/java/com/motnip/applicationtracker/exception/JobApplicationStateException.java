package com.motnip.applicationtracker.exception;

public class JobApplicationStateException extends RuntimeException {

    public JobApplicationStateException(String currentState) {
        super("No valid transition from starting status: " + currentState);
    }
}
