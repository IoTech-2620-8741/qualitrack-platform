package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.equipment.interfaces.events.CalibrationExpiredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Handles equipment calibration expiration integration events by creating critical deviation alerts.
 */
@Service
@Slf4j
public class CalibrationExpiredEventHandler {

    private final CaCommandService caCommandService;

    /**
     * Creates a new CalibrationExpiredEventHandler.
     *
     * @param caCommandService CA command service
     */
    public CalibrationExpiredEventHandler(CaCommandService caCommandService) {
        this.caCommandService = caCommandService;
    }

    /**
     * Handles the published CalibrationExpiredIntegrationEvent from the Equipment bounded context.
     *
     * @param event the calibration expired integration event
     */
    @EventListener(CalibrationExpiredIntegrationEvent.class)
    public void on(CalibrationExpiredIntegrationEvent event) {
        log.warn(
                "CA received calibration expired integration event for equipment ID '{}'.",
                event.equipmentId()
        );

        caCommandService.handle(new CreateDeviationAlertCommand(
                event.equipmentId(),
                null,
                "CALIBRATION_STATUS",
                1.0,
                0.0,
                "status",
                Instant.now().toString(),
                AlertSeverity.CRITICAL
        ));
    }
}