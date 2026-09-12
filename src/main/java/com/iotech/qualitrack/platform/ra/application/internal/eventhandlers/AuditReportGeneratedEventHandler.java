package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ra.domain.model.events.AuditReportGeneratedEvent;
import com.iotech.qualitrack.platform.ra.interfaces.events.AuditReportGeneratedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for {@link AuditReportGeneratedEvent}.
 *
 * <p>Publishes the Reporting & Audit integration event after an audit report
 * is generated, exposing RA's published language to other bounded contexts.</p>
 */
@Service
@Slf4j
public class AuditReportGeneratedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public AuditReportGeneratedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the {@link AuditReportGeneratedEvent}.
     *
     * @param event the {@link AuditReportGeneratedEvent} published by the RA bounded context
     */
    @EventListener(AuditReportGeneratedEvent.class)
    public void on(AuditReportGeneratedEvent event) {
        log.info(
                "Audit report generated: Report ID='{}', Laboratory ID='{}', Batch ID='{}', Equipment ID='{}', GeneratedBy='{}', Type='{}', GeneratedAt='{}'.",
                event.reportId(),
                event.laboratoryId(),
                event.batchId(),
                event.equipmentId(),
                event.generatedBy(),
                event.reportType(),
                event.generatedAt()
        );

        eventPublisher.publishEvent(AuditReportGeneratedIntegrationEvent.from(event));
    }
}