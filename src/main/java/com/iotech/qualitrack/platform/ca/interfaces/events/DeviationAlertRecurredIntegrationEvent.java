package com.iotech.qualitrack.platform.ca.interfaces.events;

import com.iotech.qualitrack.platform.ca.domain.model.events.DeviationAlertRecurredEvent;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;

import java.time.Instant;

/**
 * Integration event published when the condition of an open alert had returned to normal and deviated again.
 */
public record DeviationAlertRecurredIntegrationEvent(
        Long alertId,
        Long laboratoryId,
        Long environmentId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String unit,
        AlertSeverity severity,
        Instant detectedAt,
        Integer deviationCount
) {
    public static DeviationAlertRecurredIntegrationEvent from(DeviationAlertRecurredEvent event) {
        return new DeviationAlertRecurredIntegrationEvent(event.alertId(), event.laboratoryId(), event.environmentId(),
                event.equipmentId(), event.parameterName(), event.recordedValue(), event.unit(), event.severity(),
                event.detectedAt(), event.deviationCount());
    }
}
