package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.interfaces.events.BatchCreatedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchRejectedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.BatchReleasedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.RawMaterialLinkedToBatchIntegrationEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.interfaces.acl.RaContextFacade;
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

    private final RaContextFacade raContextFacade;

    public BatchAuditEventHandler(RaContextFacade raContextFacade,
            com.iotech.qualitrack.platform.shared.application.security.CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.raContextFacade = raContextFacade;
    }

    @EventListener(BatchCreatedIntegrationEvent.class)
    public void on(BatchCreatedIntegrationEvent event) {
        log.info("RA received batch created event: {}", event);

        raContextFacade.recordAuditLog(
                AuditAction.REGISTER,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        );
    }

    @EventListener(BatchReleasedIntegrationEvent.class)
    public void on(BatchReleasedIntegrationEvent event) {
        log.info("RA received batch released event: {}", event);

        raContextFacade.recordAuditLog(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        );
    }

    @EventListener(BatchRejectedIntegrationEvent.class)
    public void on(BatchRejectedIntegrationEvent event) {
        log.info("RA received batch rejected event: {}", event);

        raContextFacade.recordAuditLog(
                AuditAction.UPDATE,
                "BATCH",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        );
    }

    @EventListener(RawMaterialLinkedToBatchIntegrationEvent.class)
    public void on(RawMaterialLinkedToBatchIntegrationEvent event) {
        log.info("RA received raw material linked to batch event: {}", event);

        raContextFacade.recordAuditLog(
                AuditAction.REGISTER,
                "RAW_MATERIAL_USAGE",
                event.batchId(),
                currentUser.userId(),
                event.toString()
        );
    }
}
