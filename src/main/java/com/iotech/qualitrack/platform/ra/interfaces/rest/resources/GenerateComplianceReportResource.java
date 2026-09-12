package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;

/**
 * Resource used to request generation of a regulatory compliance report.
 *
 * @param startDate The beginning of the reporting period.
 * @param endDate The end of the reporting period.
 * @param format The requested output format.
 * @param requestedBy The numeric identifier of the requesting user.
 */
public record GenerateComplianceReportResource(
        String startDate,
        String endDate,
        ReportFormat format,
        Long requestedBy
) {
}