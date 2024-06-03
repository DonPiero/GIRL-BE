package com.example.girlbe.model.enums;

import lombok.Getter;

@Getter
public enum Rank {
    BEGINNER(1, "Beginner"),
    INTERMEDIATE(2, "Intermediate"),
    ADVANCED(3, "Advanced"),
    EXPERT(4, "Expert"),
    MASTER(5, "Master");

    private final Integer value;
    private final String name;

    Rank(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
