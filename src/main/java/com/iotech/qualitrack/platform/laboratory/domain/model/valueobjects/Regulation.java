package com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects;

public record Regulation(String code) {
    public Regulation {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Regulation code cannot be null or blank");
        }
    }
}