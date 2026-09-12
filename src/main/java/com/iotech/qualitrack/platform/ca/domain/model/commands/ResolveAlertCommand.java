package com.iotech.qualitrack.platform.ca.domain.model.commands;

/**
 * Command to request the resolution of a deviation alert.
 *
 * @param alertId The numeric identifier of the deviation alert. Cannot be null or less than 1.
 * @param resolvedBy The numeric identifier of the user resolving the alert. Cannot be null or less than 1.
 * @param resolutionNotes Notes describing the corrective action or resolution. Cannot be null or blank.
 */
public record ResolveAlertCommand(
        Long alertId,
        Long resolvedBy,
        String resolutionNotes
) {
    /**
     * Compact constructor for ResolveAlertCommand.
     * Enforces Fail-Fast validation.
     */
    public ResolveAlertCommand {
        if (alertId == null || alertId <= 0) {
            throw new IllegalArgumentException("alertId cannot be null or less than 1");
        }
        if (resolvedBy == null || resolvedBy <= 0) {
            throw new IllegalArgumentException("resolvedBy cannot be null or less than 1");
        }
        if (resolutionNotes == null || resolutionNotes.isBlank()) {
            throw new IllegalArgumentException("resolutionNotes cannot be null or blank");
        }
    }
}