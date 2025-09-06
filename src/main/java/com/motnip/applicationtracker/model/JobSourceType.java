package com.motnip.applicationtracker.model;

public enum JobSourceType {

    INDEED("Indeed", "indeed.de"),
    LINKEDIN("LinkedId", "linkedin.com"),
    XING("Xing", "xing.de"),
    JOIN("Join", "join.de"),
    HIRING_COMPANY("Company web-site", "-");

    private String sourceTypeName;
    private String soureURL;

    JobSourceType(String jobPortalName, String url) {
        this.sourceTypeName = jobPortalName;
        this.soureURL = url;
    }

    public String sourceTypeName() {
        return sourceTypeName;
    }

    public String sourceURL() {
        return soureURL;
    }

    public static JobSourceType getJobSourceTypeByName(String sourceTypeName) {
        for (JobSourceType jobPortal : values()) {
            if (jobPortal.sourceTypeName.equals(sourceTypeName)) return jobPortal;
        }
        return null;
    }

}
