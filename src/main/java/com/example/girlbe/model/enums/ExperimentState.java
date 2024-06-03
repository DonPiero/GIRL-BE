package com.example.girlbe.model.enums;

public enum ExperimentState {
    PENDING(1, "Pending"),
    COMPLETED(2, "Completed");

    private final Integer value;
    private final String name;

    ExperimentState(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
