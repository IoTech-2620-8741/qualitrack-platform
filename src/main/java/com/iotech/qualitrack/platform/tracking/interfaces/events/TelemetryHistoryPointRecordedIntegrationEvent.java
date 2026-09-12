package com.iotech.qualitrack.platform.tracking.interfaces.events;

import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryHistoryPointRecordedEvent;

/**
 * Integration event published when a telemetry history point has been recorded.
 *
 * <p>This event exposes telemetry history changes to other bounded contexts
 * without leaking Tracking internal domain events.</p>
 *
 * @param historyPointId The telemetry history point identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The recorded parameter name.
 * @param recordedValue The recorded value.
 * @param timestamp The timestamp when the point was recorded.
 * @param isAnomaly Whether the recorded point represents an anomaly.
 */
public record TelemetryHistoryPointRecordedIntegrationEvent(
        Long historyPointId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String timestamp,
        Boolean isAnomaly
) {

    /**
     * Creates an integration event from the internal Tracking domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static TelemetryHistoryPointRecordedIntegrationEvent from(TelemetryHistoryPointRecordedEvent event) {
        return new TelemetryHistoryPointRecordedIntegrationEvent(
                event.historyPointId(),
                event.equipmentId(),
                event.parameterName(),
                event.recordedValue(),
                event.timestamp(),
                event.isAnomaly()
        );
    }
}