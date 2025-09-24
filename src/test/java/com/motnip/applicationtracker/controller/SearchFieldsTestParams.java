package com.motnip.applicationtracker.controller;

import com.motnip.applicationtracker.model.ApplicationState;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchFieldsTestParams{

    private String companyName;
    private ApplicationState state;
    private int excpectedMatchingResultNumber;

    public SearchFieldsTestParams(String companyName, ApplicationState state, int excpectedMatchingResultNumber) {
        this.companyName = companyName;
        this.state = state;
        this.excpectedMatchingResultNumber = excpectedMatchingResultNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ApplicationState getState() {
        return state;
    }

    public int getExcpectedMatchingResultNumber() {
        return excpectedMatchingResultNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("companyName", companyName)
                .append("state", state)
                .append("excpectedMatchingResultNumber", excpectedMatchingResultNumber)
                .toString();
    }
}

