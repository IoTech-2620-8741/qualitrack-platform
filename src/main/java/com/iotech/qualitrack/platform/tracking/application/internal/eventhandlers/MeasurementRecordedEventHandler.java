package com.iotech.qualitrack.platform.tracking.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.tracking.domain.model.events.MeasurementRecordedEvent;
import com.iotech.qualitrack.platform.tracking.interfaces.events.MeasurementRecordedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal measurement recorded events and publishes Tracking integration events.
 */
@Service
@Slf4j
public class MeasurementRecordedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public MeasurementRecordedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles a measurement recorded domain event.
     *
     * @param event the internal Tracking domain event
     */
    @EventListener(MeasurementRecordedEvent.class)
    public void on(MeasurementRecordedEvent event) {
        log.info(
                "Telemetry measurement recorded: Measurement ID='{}', Equipment ID='{}', Parameter='{}', Value='{}', Unit='{}', Timestamp='{}'.",
                event.measurementId(),
                event.equipmentId(),
                event.parameterName(),
                event.value(),
                event.unit(),
                event.timestamp()
        );

        eventPublisher.publishEvent(MeasurementRecordedIntegrationEvent.from(event));
    }
}