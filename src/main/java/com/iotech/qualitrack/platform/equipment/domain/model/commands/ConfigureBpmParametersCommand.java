package com.iotech.qualitrack.platform.equipment.domain.model.commands;

/**
 * Command to configure a BPM (Business Process Management) parameter for an equipment.
 *
 * @param equipmentId The numeric identifier of the equipment. Cannot be null or less than 1.
 * @param parameterName The name of the parameter to configure. Cannot be null or blank.
 * @param minValue The minimum acceptable value. Cannot be null.
 * @param maxValue The maximum acceptable value. Cannot be null.
 * @param unit The measurement unit. Cannot be null or blank.
 */
public record ConfigureBpmParametersCommand(
        Long equipmentId,
        String parameterName,
        Double minValue,
        Double maxValue,
        String unit
) {
    /**
     * Compact constructor for ConfigureBpmParametersCommand.
     * Enforces Fail-Fast validation.
     */
    public ConfigureBpmParametersCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("parameterName cannot be null or blank");
        }
        if (minValue == null) {
            throw new IllegalArgumentException("minValue cannot be null");
        }
        if (maxValue == null) {
            throw new IllegalArgumentException("maxValue cannot be null");
        }
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be strictly less than maxValue");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit cannot be null or blank");
        }
    }
}