package com.motnip.applicationtracker.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.motnip.applicationtracker.model.ApplicationStatus.*;

@Component
public class ApplicationStateHandler {


    private HashMap<ApplicationStatus, List<ApplicationStatus>> stateTransitions;

    public ApplicationStateHandler() {
        stateTransitions = new HashMap<>();
        stateTransitions.put(WAITING, Arrays.asList(EXPIRED, REJECTED, IN_PROGRESS));
        stateTransitions.put(IN_PROGRESS, Arrays.asList(REJECTED, CONFIRMED, CANCELED));
    }

    public List<ApplicationStatus> getNextStates(ApplicationStatus currentState) {
        var optionalNextStates = Optional.ofNullable(stateTransitions.get(currentState));
        if (optionalNextStates.isPresent()) {
            return optionalNextStates.get();
        } else {
            throw new IllegalStateException("No valid transition for status: " + currentState);
        }
    }

    public ApplicationStatus validateStateChange(ApplicationStatus currentStates, ApplicationStatus nextState) {

        List<ApplicationStatus> nextValidStates = getNextStates(currentStates);
        if (!nextValidStates.contains(nextState)) {
            throw new IllegalStateException("Invalid status transition from " + currentStates + " to " + nextState);
        }
        return nextState;
    }
}
