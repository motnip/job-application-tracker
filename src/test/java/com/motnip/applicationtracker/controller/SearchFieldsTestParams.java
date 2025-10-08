package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.model.ApplicationState;

import java.util.List;

public class SearchFieldsTestParams {

    private String companyName;
    private List<ApplicationState> states;
    private int excpectedMatchingResultNumber;

    public SearchFieldsTestParams(String companyName, List<ApplicationState> states, int excpectedMatchingResultNumber) {
        this.companyName = companyName;
        this.states = states;
        this.excpectedMatchingResultNumber = excpectedMatchingResultNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<ApplicationState> getState() {
        return states;
    }

    public int getExcpectedMatchingResultNumber() {
        return excpectedMatchingResultNumber;
    }

}

