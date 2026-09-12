package com.iotech.qualitrack.platform.ca.interfaces.events;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertAcknowledgedEvent;

/**
 * Integration event published by the Compliance and Alerts bounded context when a deviation alert is acknowledged.
 *
 * <p>This event allows audit, reporting, and notification workflows to react
 * without depending on CA internal domain events.</p>
 *
 * @param alertId the acknowledged alert identifier
 * @param equipmentId the equipment that generated the alert
 * @param batchId the related batch identifier, if applicable
 * @param acknowledgedBy the user who acknowledged the alert
 */
public record DeviationAlertAcknowledgedIntegrationEvent(
        Long alertId,
        Long equipmentId,
        Long batchId,
        Long acknowledgedBy
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal deviation alert acknowledged domain event
     * @return the integration event
     */
    public static DeviationAlertAcknowledgedIntegrationEvent from(
            DeviationAlertAcknowledgedEvent event
    ) {
        return new DeviationAlertAcknowledgedIntegrationEvent(
                event.alertId(),
                event.equipmentId(),
                event.batchId(),
                event.acknowledgedBy()
        );
    }
}