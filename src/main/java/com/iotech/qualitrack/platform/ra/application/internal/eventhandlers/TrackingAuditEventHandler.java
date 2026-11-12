package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import com.iotech.qualitrack.platform.shared.application.security.CurrentUser;
import com.iotech.qualitrack.platform.tracking.interfaces.events.EnvironmentalProfileUpdatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Records in the audit log who changed the thresholds or actuation rules of an environmental profile. The readings
 * and actions of the devices stay in Tracking &amp; Telemetry, which Reporting queries for its reports.
 */
@Service
@Slf4j
public class TrackingAuditEventHandler {
    private final CurrentUser currentUser;
    private final AuditLogCommandService auditLogCommandService;

    public TrackingAuditEventHandler(AuditLogCommandService auditLogCommandService, CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.auditLogCommandService = auditLogCommandService;
    }

    @EventListener(EnvironmentalProfileUpdatedIntegrationEvent.class)
    public void on(EnvironmentalProfileUpdatedIntegrationEvent event) {
        log.info("RA received environmental profile updated event: {}", event);
        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "ENVIRONMENTAL_PROFILE",
                event.profileId(),
                currentUser.userId(),
                event.toString()
        ));
    }
}
