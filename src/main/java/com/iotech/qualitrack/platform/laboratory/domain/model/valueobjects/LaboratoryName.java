package com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects;

public record LaboratoryName(String name) {
    public LaboratoryName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Laboratory name cannot be null or blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Laboratory name cannot exceed 100 characters");
        }
    }
}