package com.iotech.qualitrack.platform.ca.interfaces.events;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertResolvedEvent;

/**
 * Integration event published by the Compliance and Alerts bounded context when a deviation alert is resolved.
 *
 * <p>This event allows audit and reporting workflows to record the corrective
 * action outcome without depending on CA internals.</p>
 *
 * @param alertId the resolved alert identifier
 * @param equipmentId the equipment that generated the alert
 * @param batchId the related batch identifier, if applicable
 * @param resolvedBy the user who resolved the alert
 * @param resolutionNotes the resolution or corrective action notes
 */
public record DeviationAlertResolvedIntegrationEvent(
        Long alertId,
        Long equipmentId,
        Long batchId,
        Long resolvedBy,
        String resolutionNotes
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal deviation alert resolved domain event
     * @return the integration event
     */
    public static DeviationAlertResolvedIntegrationEvent from(DeviationAlertResolvedEvent event) {
        return new DeviationAlertResolvedIntegrationEvent(
                event.alertId(),
                event.equipmentId(),
                event.batchId(),
                event.resolvedBy(),
                event.resolutionNotes()
        );
    }
}