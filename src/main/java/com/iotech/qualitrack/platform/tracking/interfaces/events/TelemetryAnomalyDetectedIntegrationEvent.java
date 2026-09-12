package com.iotech.qualitrack.platform.tracking.interfaces.events;

import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryAnomalyDetectedEvent;

/**
 * Integration event published when a telemetry anomaly has been detected.
 *
 * <p>This event can be consumed by Compliance & Alerts or Reporting & Audit
 * without depending on Tracking internal domain events.</p>
 *
 * @param historyPointId The anomalous telemetry history point identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The anomalous parameter name.
 * @param recordedValue The recorded anomalous value.
 * @param timestamp The timestamp when the anomalous point was recorded.
 */
public record TelemetryAnomalyDetectedIntegrationEvent(
        Long historyPointId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp
) {

    /**
     * Creates an integration event from the internal Tracking domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static TelemetryAnomalyDetectedIntegrationEvent from(TelemetryAnomalyDetectedEvent event) {
        return new TelemetryAnomalyDetectedIntegrationEvent(
                event.historyPointId(),
                event.equipmentId(),
                event.parameterName(),
                event.recordedValue(),
                event.timestamp()
        );
    }
}