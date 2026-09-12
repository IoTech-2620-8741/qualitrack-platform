package com.iotech.qualitrack.platform.ca.domain.model.events;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;

/**
 * Domain event published when a deviation alert is resolved.
 *
 * <p>Can be used by compliance audit, reports, and notification workflows to
 * register the corrective action applied to a deviation.</p>
 *
 * @param alertId The numeric identity of the resolved alert.
 * @param equipmentId The numeric identity of the equipment that generated the alert.
 * @param batchId The numeric identity of the related batch, if applicable.
 * @param resolvedBy The numeric identity of the user who resolved the alert.
 * @param resolutionNotes Notes describing the corrective action or resolution.
 */
public record DeviationAlertResolvedEvent(
        Long alertId,
        Long equipmentId,
        Long batchId,
        Long resolvedBy,
        String resolutionNotes
) {
    /**
     * Convenience factory that extracts all needed fields from a resolved {@link DeviationAlert}.
     *
     * @param alert the resolved deviation alert
     * @return a fully populated {@link DeviationAlertResolvedEvent}
     */
    public static DeviationAlertResolvedEvent from(DeviationAlert alert) {
        return new DeviationAlertResolvedEvent(
                alert.getId(),
                alert.getEquipmentId(),
                alert.getBatchId(),
                alert.getResolvedBy(),
                alert.getResolutionNotes()
        );
    }
}