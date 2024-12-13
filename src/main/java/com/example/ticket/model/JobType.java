package com.example.ticket.model;


public enum JobType {
    PHYSICAL("Pracownik fizyczny"),
    OFFICE("Pracownik biurowy");

    private final String displayName;

    JobType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
