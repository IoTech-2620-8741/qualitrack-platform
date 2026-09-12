package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;

/**
 * Resource representation of a generated audit report.
 *
 * @param id The unique numeric identifier of the report.
 * @param laboratoryId The numeric identifier of the laboratory.
 * @param batchId The numeric identifier of the batch, if applicable.
 * @param equipmentId The numeric identifier of the equipment, if applicable.
 * @param generatedBy The numeric identifier of the user who generated the report.
 * @param generatedByName The display name of the user who generated the report.
 * @param reportType The generated report type.
 * @param dateRangeFrom The beginning of the reporting period, if applicable.
 * @param dateRangeTo The end of the reporting period, if applicable.
 * @param filePath The stored file path or logical file reference.
 * @param checksum The checksum of the generated content.
 * @param generatedAt The timestamp when the report was generated.
 */
public record AuditReportResource(
        Long id,
        Long laboratoryId,
        Long batchId,
        Long equipmentId,
        Long generatedBy,
        String generatedByName,
        ReportType reportType,
        String dateRangeFrom,
        String dateRangeTo,
        String filePath,
        String checksum,
        String generatedAt
) {
}