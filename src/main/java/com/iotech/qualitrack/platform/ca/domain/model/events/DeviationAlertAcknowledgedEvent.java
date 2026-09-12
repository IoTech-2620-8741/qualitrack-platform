package com.iotech.qualitrack.platform.ca.domain.model.events;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;

/**
 * Domain event published when a deviation alert is acknowledged.
 *
 * <p>Can be used by compliance audit or notification workflows to record that
 * a responsible user has reviewed the alert.</p>
 *
 * @param alertId The numeric identity of the acknowledged alert.
 * @param equipmentId The numeric identity of the equipment that generated the alert.
 * @param batchId The numeric identity of the related batch, if applicable.
 * @param acknowledgedBy The numeric identity of the user who acknowledged the alert.
 */
public record DeviationAlertAcknowledgedEvent(
        Long alertId,
        Long equipmentId,
        Long batchId,
        Long acknowledgedBy
) {
    /**
     * Convenience factory that extracts all needed fields from an acknowledged {@link DeviationAlert}.
     *
     * @param alert the acknowledged deviation alert
     * @return a fully populated {@link DeviationAlertAcknowledgedEvent}
     */
    public static DeviationAlertAcknowledgedEvent from(DeviationAlert alert) {
        return new DeviationAlertAcknowledgedEvent(
                alert.getId(),
                alert.getEquipmentId(),
                alert.getBatchId(),
                alert.getAcknowledgedBy()
        );
    }
}