package com.iotech.qualitrack.platform.tracking.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryHistoryPointRecordedEvent;
import com.iotech.qualitrack.platform.tracking.interfaces.events.TelemetryHistoryPointRecordedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal telemetry history point recorded events and publishes Tracking integration events.
 */
@Service
@Slf4j
public class TelemetryHistoryPointRecordedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public TelemetryHistoryPointRecordedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles a telemetry history point recorded domain event.
     *
     * @param event the internal Tracking domain event
     */
    @EventListener(TelemetryHistoryPointRecordedEvent.class)
    public void on(TelemetryHistoryPointRecordedEvent event) {
        log.info(
                "Telemetry history point recorded: History Point ID='{}', Equipment ID='{}', Parameter='{}', Value='{}', Timestamp='{}', IsAnomaly='{}'.",
                event.historyPointId(),
                event.equipmentId(),
                event.parameterName(),
                event.recordedValue(),
                event.timestamp(),
                event.isAnomaly()
        );

        eventPublisher.publishEvent(TelemetryHistoryPointRecordedIntegrationEvent.from(event));
    }
}