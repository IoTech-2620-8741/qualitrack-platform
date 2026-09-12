package com.iotech.qualitrack.platform.tracking.interfaces.events;

import com.iotech.qualitrack.platform.tracking.domain.model.events.MeasurementRecordedEvent;

/**
 * Integration event published when a telemetry measurement has been recorded.
 *
 * <p>This is part of the published language of the Tracking bounded context.</p>
 *
 * @param measurementId The recorded measurement identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The measured parameter name.
 * @param value The measured value.
 * @param unit The measurement unit.
 * @param timestamp The timestamp when the measurement was recorded.
 */
public record MeasurementRecordedIntegrationEvent(
        Long measurementId,
        Long equipmentId,
        String parameterName,
        Double value,
        String unit,
        String timestamp
) {

    /**
     * Creates an integration event from the internal Tracking domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static MeasurementRecordedIntegrationEvent from(MeasurementRecordedEvent event) {
        return new MeasurementRecordedIntegrationEvent(
                event.measurementId(),
                event.equipmentId(),
                event.parameterName(),
                event.value(),
                event.unit(),
                event.timestamp()
        );
    }
}