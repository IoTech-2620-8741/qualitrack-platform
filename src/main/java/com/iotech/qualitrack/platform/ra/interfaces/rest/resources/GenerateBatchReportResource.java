package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;

/**
 * Resource used to request generation of a production batch report.
 *
 * @param includeTelemetry Indicates whether telemetry data should be included.
 * @param includeDeviations Indicates whether deviation alerts should be included.
 * @param format The requested output format.
 * @param requestedBy The numeric identifier of the requesting user.
 */
public record GenerateBatchReportResource(
        Boolean includeTelemetry,
        Boolean includeDeviations,
        ReportFormat format,
        Long requestedBy
) {
}