package com.iotech.qualitrack.platform.equipment.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.equipment.domain.model.events.CalibrationExpiredEvent;
import com.iotech.qualitrack.platform.equipment.interfaces.events.CalibrationExpiredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal CalibrationExpiredEvent events and publishes the corresponding integration event.
 */
@Service
@Slf4j
public class CalibrationExpiryEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new CalibrationExpiryEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public CalibrationExpiryEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal calibration expired domain event.
     *
     * @param event the internal calibration expired domain event
     */
    @EventListener(CalibrationExpiredEvent.class)
    public void on(CalibrationExpiredEvent event) {
        log.warn(
                "Calibration expired. equipmentId={}, equipmentName={}, serialNumber={}, laboratoryId={}",
                event.equipmentId(),
                event.equipmentName(),
                event.serialNumber(),
                event.laboratoryId()
        );

        applicationEventPublisher.publishEvent(CalibrationExpiredIntegrationEvent.from(event));
    }
}