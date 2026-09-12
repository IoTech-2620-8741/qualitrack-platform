package com.iotech.qualitrack.platform.ra.interfaces.events;

import com.iotech.qualitrack.platform.ra.domain.model.events.AuditReportGeneratedEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;

/**
 * Integration event published when an audit report has been generated.
 *
 * <p>This is part of the published language of the Reporting & Audit bounded context.</p>
 *
 * @param reportId The generated report identifier.
 * @param laboratoryId The related laboratory identifier, if applicable.
 * @param batchId The related batch identifier, if applicable.
 * @param equipmentId The related equipment identifier, if applicable.
 * @param generatedBy The user or agent who generated the report.
 * @param reportType The type of generated report.
 * @param generatedAt The timestamp when the report was generated.
 */
public record AuditReportGeneratedIntegrationEvent(
        Long reportId,
        Long laboratoryId,
        Long batchId,
        Long equipmentId,
        Long generatedBy,
        ReportType reportType,
        String generatedAt
) {

    /**
     * Creates an integration event from the internal RA domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static AuditReportGeneratedIntegrationEvent from(AuditReportGeneratedEvent event) {
        return new AuditReportGeneratedIntegrationEvent(
                event.reportId(),
                event.laboratoryId(),
                event.batchId(),
                event.equipmentId(),
                event.generatedBy(),
                event.reportType(),
                event.generatedAt()
        );
    }
}