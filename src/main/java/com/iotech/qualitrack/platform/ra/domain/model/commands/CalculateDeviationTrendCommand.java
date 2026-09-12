package com.iotech.qualitrack.platform.ra.domain.model.commands;

/**
 * Command used to calculate a deviation trend for an equipment parameter.
 *
 * @param equipmentId The equipment identifier.
 * @param parameterName The monitored parameter name.
 */
public record CalculateDeviationTrendCommand(
        Long equipmentId,
        String parameterName
) {

    /**
     * Compact constructor for validation.
     */
    public CalculateDeviationTrendCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("parameterName cannot be null or blank");
        }
    }
}