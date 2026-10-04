package com.iotech.qualitrack.platform.ca.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.tracking.interfaces.events.EnvironmentalDeviationDetectedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Creates the alert of an environmental deviation detected by Tracking &amp; Telemetry, with the severity of the
 * condition (WARNING or CRITICAL), the limit crossed and its unit.
 */
@Service
@Slf4j
public class EnvironmentalDeviationComplianceEventHandler {
    private final CaCommandService caCommandService;

    public EnvironmentalDeviationComplianceEventHandler(CaCommandService caCommandService) {
        this.caCommandService = caCommandService;
    }

    @EventListener(EnvironmentalDeviationDetectedIntegrationEvent.class)
    public void on(EnvironmentalDeviationDetectedIntegrationEvent event) {
        log.warn("CA received an environmental deviation: device={}, metric={}, value={} {}, state={}, limit={}",
                event.deviceId(), event.metric(), event.value(), event.unit(), event.state(), event.thresholdValue());
        caCommandService.handle(new CreateDeviationAlertCommand(
                event.deviceId(),
                null,
                event.metric(),
                event.value(),
                event.thresholdValue(),
                event.unit(),
                event.measuredAt().toString(),
                AlertSeverity.valueOf(event.state())
        ));
    }
}
