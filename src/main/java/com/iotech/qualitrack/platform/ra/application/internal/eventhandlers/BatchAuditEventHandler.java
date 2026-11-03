package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.interfaces.events.BatchCreatedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchParticipationRegisteredIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchStoredInContainerIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchRejectedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchReleasedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchStartedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.ProductCreatedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.RawMaterialLinkedToBatchIntegrationEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles Batch integration events and records audit logs in RA.
 */
@Service
@Slf4j
public class BatchAuditEventHandler {

    private final com.iotech.qualitrack.platform.shared.application.security.CurrentUser currentUser;

    private final AuditLogCommandService auditLogCommandService;

    public BatchAuditEventHandler(AuditLogCommandService auditLogCommandService,
            com.iotech.qualitrack.platform.shared.application.security.CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.auditLogCommandService = auditLogCommandService;
    }

    @EventListener(BatchCreatedIntegrationEvent.class)
    public void on(BatchCreatedIntegrationEvent event) {
        log.info("RA received batch created event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(BatchStartedIntegrationEvent.class)
    public void on(BatchStartedIntegrationEvent event) {
        log.info("RA received batch started event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(BatchReleasedIntegrationEvent.class)
    public void on(BatchReleasedIntegrationEvent event) {
        log.info("RA received batch released event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(BatchRejectedIntegrationEvent.class)
    public void on(BatchRejectedIntegrationEvent event) {
        log.info("RA received batch rejected event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(RawMaterialLinkedToBatchIntegrationEvent.class)
    public void on(RawMaterialLinkedToBatchIntegrationEvent event) {
        log.info("RA received raw material linked to batch event: {}", event);

        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "RAW_MATERIAL_USAGE",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(ProductCreatedIntegrationEvent.class)
    public void on(ProductCreatedIntegrationEvent event) {
        log.info("RA received product created event: {}", event);
        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "PHARMACEUTICAL_PRODUCT",
                event.productId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(BatchParticipationRegisteredIntegrationEvent.class)
    public void on(BatchParticipationRegisteredIntegrationEvent event) {
        log.info("RA received batch participation event: {}", event);
        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.REGISTER,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }

    @EventListener(BatchStoredInContainerIntegrationEvent.class)
    public void on(BatchStoredInContainerIntegrationEvent event) {
        log.info("RA received batch storage event: {}", event);
        auditLogCommandService.handle(new RecordAuditLogEntryCommand(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        ));
    }
}
