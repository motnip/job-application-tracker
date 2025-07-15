package com.motnip.applicationtracker.model;

import java.util.*;

import static com.motnip.applicationtracker.model.ApplicationState.*;

public class ApplicationStateHandler {


    private static Map<ApplicationState, List<ApplicationState>> stateTransitions = Map.ofEntries(
            Map.entry(WAITING, Arrays.asList(EXPIRED, REJECTED, IN_PROGRESS)),
            Map.entry(IN_PROGRESS, Arrays.asList(REJECTED, CONFIRMED, CANCELED))
    );

    public static boolean validateStateChange(ApplicationState currentState, ApplicationState nextState) {
        return getNextStates(currentState).contains(nextState);
    }

    private static List<ApplicationState> getNextStates(ApplicationState currentState) {
        var optionalNextStates = Optional.ofNullable(stateTransitions.get(currentState));
        return optionalNextStates.orElse(Collections.emptyList());
    }
}
