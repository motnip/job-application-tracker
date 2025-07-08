package com.motnip.applicationtracker.model;

import com.motnip.applicationtracker.exception.JobApplicationStateException;

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

    private static List<ApplicationState> getNextStates(ApplicationState currentState) {
        var optionalNextStates = Optional.ofNullable(stateTransitions.get(currentState));
        if (optionalNextStates.isPresent()) {
            return optionalNextStates.get();
        } else {
            throw new JobApplicationStateException(currentState.name());
        }
    }

    public static boolean validateStateChange(ApplicationState currentState, ApplicationState nextState) {

        if (!getNextStates(currentState).contains(nextState)) {
            throw new JobApplicationStateException(currentState.name());
        }
        return true;
    }
}
