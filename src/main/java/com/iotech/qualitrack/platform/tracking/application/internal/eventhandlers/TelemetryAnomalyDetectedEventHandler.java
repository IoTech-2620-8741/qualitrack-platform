package com.iotech.qualitrack.platform.tracking.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryAnomalyDetectedEvent;
import com.iotech.qualitrack.platform.tracking.interfaces.events.TelemetryAnomalyDetectedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal telemetry anomaly detected events and publishes Tracking integration events.
 */
@Service
@Slf4j
public class TelemetryAnomalyDetectedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public TelemetryAnomalyDetectedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles a telemetry anomaly detected domain event.
     *
     * @param event the internal Tracking domain event
     */
    @EventListener(TelemetryAnomalyDetectedEvent.class)
    public void on(TelemetryAnomalyDetectedEvent event) {
        log.warn(
                "Telemetry anomaly detected: History Point ID='{}', Equipment ID='{}', Parameter='{}', Value='{}', Timestamp='{}'.",
                event.historyPointId(),
                event.equipmentId(),
                event.parameterName(),
                event.recordedValue(),
                event.timestamp()
        );

        eventPublisher.publishEvent(TelemetryAnomalyDetectedIntegrationEvent.from(event));
    }
}