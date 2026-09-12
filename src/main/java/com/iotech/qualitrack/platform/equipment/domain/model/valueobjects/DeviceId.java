package com.iotech.qualitrack.platform.equipment.domain.model.valueobjects;

public record DeviceId(String value) {
    public DeviceId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Device ID cannot be null or blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("Device ID cannot exceed 50 characters");
        }
    }
}