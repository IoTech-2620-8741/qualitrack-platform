package com.iotech.qualitrack.platform.ca.domain.model.commands;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;

/**
 * Command to request the creation of a new deviation alert.
 *
 * @param equipmentId The numeric identifier of the equipment that generated the alert. Cannot be null or less than 1.
 * @param batchId The numeric identifier of the production batch associated with the alert, if applicable.
 * @param parameterName The name of the monitored parameter. Cannot be null or blank.
 * @param recordedValue The measured value that triggered the deviation. Cannot be null.
 * @param thresholdValue The configured threshold value that was exceeded. Cannot be null.
 * @param unit The measurement unit. Cannot be null or blank.
 * @param timestamp The timestamp when the deviation occurred. Cannot be null or blank.
 * @param severity The impact level of the alert. Cannot be null.
 */
public record CreateDeviationAlertCommand(
        Long equipmentId,
        Long batchId,
        String parameterName,
        Double recordedValue,
        Double thresholdValue,
        String unit,
        String timestamp,
        AlertSeverity severity
) {
    /**
     * Compact constructor for CreateDeviationAlertCommand.
     * Enforces Fail-Fast validation.
     */
    public CreateDeviationAlertCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (batchId != null && batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be less than 1");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("parameterName cannot be null or blank");
        }
        if (recordedValue == null) {
            throw new IllegalArgumentException("recordedValue cannot be null");
        }
        if (thresholdValue == null) {
            throw new IllegalArgumentException("thresholdValue cannot be null");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit cannot be null or blank");
        }
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("timestamp cannot be null or blank");
        }
        if (severity == null) {
            throw new IllegalArgumentException("severity cannot be null");
        }
    }
}