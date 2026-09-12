package com.iotech.qualitrack.platform.ra.domain.model.events;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;

/**
 * Event raised when an audit report is generated.
 *
 * @param reportId The numeric identifier of the generated report.
 * @param laboratoryId The numeric identifier of the laboratory associated with the report.
 * @param batchId The numeric identifier of the production batch, if applicable.
 * @param equipmentId The numeric identifier of the equipment, if applicable.
 * @param generatedBy The numeric identifier of the user who generated the report.
 * @param reportType The generated report type.
 * @param generatedAt The timestamp when the report was generated.
 */
public record AuditReportGeneratedEvent(
        Long reportId,
        Long laboratoryId,
        Long batchId,
        Long equipmentId,
        Long generatedBy,
        ReportType reportType,
        String generatedAt
) {
    /**
     * Compact constructor for AuditReportGeneratedEvent.
     * Enforces Fail-Fast validation.
     */
    public AuditReportGeneratedEvent {
        if (reportId == null || reportId <= 0) {
            throw new IllegalArgumentException("reportId cannot be null or less than 1");
        }
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (batchId != null && batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be less than 1");
        }
        if (equipmentId != null && equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be less than 1");
        }
        if (generatedBy == null || generatedBy <= 0) {
            throw new IllegalArgumentException("generatedBy cannot be null or less than 1");
        }
        if (reportType == null) {
            throw new IllegalArgumentException("reportType cannot be null");
        }
        if (generatedAt == null || generatedAt.isBlank()) {
            throw new IllegalArgumentException("generatedAt cannot be null or blank");
        }
    }
}