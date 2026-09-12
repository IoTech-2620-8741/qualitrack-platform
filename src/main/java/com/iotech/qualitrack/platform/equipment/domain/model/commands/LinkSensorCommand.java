package com.iotech.qualitrack.platform.equipment.domain.model.commands;

/**
 * Command to request linking an external IoT sensor to a specific equipment.
 *
 * @param equipmentId The numeric identifier of the equipment. Cannot be null or less than 1.
 * @param sensorExternalId The external identifier of the sensor. Cannot be null or blank.
 * @param sensorType The type of sensor being linked. Cannot be null or blank.
 */
public record LinkSensorCommand(
        Long equipmentId,
        String sensorExternalId,
        String sensorType
) {
    /**
     * Compact constructor for LinkSensorCommand.
     * Enforces Fail-Fast validation.
     */
    public LinkSensorCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (sensorExternalId == null || sensorExternalId.isBlank()) {
            throw new IllegalArgumentException("sensorExternalId cannot be null or blank");
        }
        if (sensorType == null || sensorType.isBlank()) {
            throw new IllegalArgumentException("sensorType cannot be null or blank");
        }
    }
}