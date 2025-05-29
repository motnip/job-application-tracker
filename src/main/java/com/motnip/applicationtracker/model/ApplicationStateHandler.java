package com.motnip.applicationtracker.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.motnip.applicationtracker.model.ApplicationState.*;

@Component
public class ApplicationStateHandler {


    private HashMap<ApplicationState, List<ApplicationState>> stateTransitions;

    public ApplicationStateHandler() {
        stateTransitions = new HashMap<>();
        stateTransitions.put(WAITING, Arrays.asList(EXPIRED, REJECTED, IN_PROGRESS));
        stateTransitions.put(IN_PROGRESS, Arrays.asList(REJECTED, CONFIRMED, CANCELED));
    }

    public List<ApplicationState> getNextStates(ApplicationState currentState) {
        var optionalNextStates = Optional.ofNullable(stateTransitions.get(currentState));
        if (optionalNextStates.isPresent()) {
            return optionalNextStates.get();
        } else {
            throw new IllegalStateException("No valid transition for status: " + currentState);
        }
    }

    public ApplicationState validateStateChange(ApplicationState currentStates, ApplicationState nextState) {

        List<ApplicationState> nextValidStates = getNextStates(currentStates);
        if (!nextValidStates.contains(nextState)) {
            throw new IllegalStateException("Invalid status transition from " + currentStates + " to " + nextState);
        }
        return nextState;
    }
}
