package com.iotech.qualitrack.platform.equipment.domain.model.valueobjects;

/**
 * Value Object representing the category or type of an equipment.
 */
public record EquipmentType(String name) {

    public EquipmentType {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Equipment type cannot be null or blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Equipment type cannot exceed 100 characters");
        }
    }
}