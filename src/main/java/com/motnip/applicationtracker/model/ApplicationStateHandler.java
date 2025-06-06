package com.motnip.applicationtracker.model;

import org.springframework.stereotype.Component;

import java.util.*;

import static com.motnip.applicationtracker.model.ApplicationState.*;


public class ApplicationStateHandler {


    private static Map<ApplicationState, List<ApplicationState>> stateTransitions = Map.ofEntries(
            Map.entry(WAITING, Arrays.asList(EXPIRED, REJECTED, IN_PROGRESS)),
            Map.entry(IN_PROGRESS, Arrays.asList(REJECTED, CONFIRMED, CANCELED))
    );

/*    public ApplicationStateHandler() {
        stateTransitions.put(WAITING, Arrays.asList(EXPIRED, REJECTED, IN_PROGRESS));
        stateTransitions.put(IN_PROGRESS, Arrays.asList(REJECTED, CONFIRMED, CANCELED));
    }*/

    public static List<ApplicationState> getNextStates(ApplicationState currentState) {
        var optionalNextStates = Optional.ofNullable(stateTransitions.get(currentState));
        if (optionalNextStates.isPresent()) {
            return optionalNextStates.get();
        } else {
            throw new IllegalStateException("No valid transition for status: " + currentState);
        }
    }

    public static boolean validateStateChange(ApplicationState currentStates, ApplicationState nextState) {

        if (!getNextStates(currentStates).contains(nextState)) {
            throw new IllegalStateException("Invalid status transition from " + currentStates + " to " + nextState);
        }
        return true;
    }
}
