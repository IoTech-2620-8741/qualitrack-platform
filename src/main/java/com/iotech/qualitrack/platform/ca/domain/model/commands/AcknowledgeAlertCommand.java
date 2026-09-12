package com.iotech.qualitrack.platform.ca.domain.model.commands;

/**
 * Command to request the acknowledgement of a deviation alert.
 *
 * @param alertId The numeric identifier of the deviation alert. Cannot be null or less than 1.
 * @param acknowledgedBy The numeric identifier of the user acknowledging the alert. Cannot be null or less than 1.
 */
public record AcknowledgeAlertCommand(
        Long alertId,
        Long acknowledgedBy
) {
    /**
     * Compact constructor for AcknowledgeAlertCommand.
     * Enforces Fail-Fast validation.
     */
    public AcknowledgeAlertCommand {
        if (alertId == null || alertId <= 0) {
            throw new IllegalArgumentException("alertId cannot be null or less than 1");
        }
        if (acknowledgedBy == null || acknowledgedBy <= 0) {
            throw new IllegalArgumentException("acknowledgedBy cannot be null or less than 1");
        }
    }
}