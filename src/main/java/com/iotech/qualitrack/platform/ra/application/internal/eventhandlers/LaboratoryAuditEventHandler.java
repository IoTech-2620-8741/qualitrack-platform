package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.interfaces.events.EnvironmentRegisteredIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.EnvironmentUpdatedIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.EnvironmentUsageAssignedIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.LaboratoryRegisteredIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.RawMaterialCreatedIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.RawMaterialLowStockIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.StaffDeactivatedIntegrationEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.StaffRegisteredIntegrationEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles Laboratory integration events and records audit logs in RA.
 */
@Service
@Slf4j
public class LaboratoryAuditEventHandler {

    private final com.iotech.qualitrack.platform.shared.application.security.CurrentUser currentUser;

    private final AuditLogCommandService auditLogCommandService;

    public LaboratoryAuditEventHandler(AuditLogCommandService auditLogCommandService,
            com.iotech.qualitrack.platform.shared.application.security.CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.auditLogCommandService = auditLogCommandService;
    }

    @EventListener(LaboratoryRegisteredIntegrationEvent.class)
    public void on(LaboratoryRegisteredIntegrationEvent event) {
        log.info("RA received laboratory registered event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "LABORATORY",
                event.laboratoryId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(RawMaterialCreatedIntegrationEvent.class)
    public void on(RawMaterialCreatedIntegrationEvent event) {
        log.info("RA received raw material created event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "RAW_MATERIAL",
                event.rawMaterialId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(StaffRegisteredIntegrationEvent.class)
    public void on(StaffRegisteredIntegrationEvent event) {
        log.info("RA received staff registered event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "STAFF_MEMBER",
                event.staffMemberId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(StaffDeactivatedIntegrationEvent.class)
    public void on(StaffDeactivatedIntegrationEvent event) {
        log.info("RA received staff deactivated event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "STAFF_MEMBER",
                event.staffMemberId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(RawMaterialLowStockIntegrationEvent.class)
    public void on(RawMaterialLowStockIntegrationEvent event) {
        log.info("RA received raw material low stock event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "RAW_MATERIAL",
                event.rawMaterialId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(EnvironmentRegisteredIntegrationEvent.class)
    public void on(EnvironmentRegisteredIntegrationEvent event) {
        log.info("RA received environment registered event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "ENVIRONMENT",
                event.environmentId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(EnvironmentUpdatedIntegrationEvent.class)
    public void on(EnvironmentUpdatedIntegrationEvent event) {
        log.info("RA received environment updated event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "ENVIRONMENT",
                event.environmentId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(EnvironmentUsageAssignedIntegrationEvent.class)
    public void on(EnvironmentUsageAssignedIntegrationEvent event) {
        log.info("RA received environment usage assigned event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "ENVIRONMENT",
                event.environmentId(),
                event.assignedBy(),
                event.toString()
        ));
    }
}
