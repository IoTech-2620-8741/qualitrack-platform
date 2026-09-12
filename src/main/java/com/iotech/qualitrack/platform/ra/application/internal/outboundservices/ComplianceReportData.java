package com.iotech.qualitrack.platform.ra.application.internal.outboundservices;

import java.time.Instant;
import java.util.List;

/** Snapshot of deviations recorded within a requested laboratory and period. */
public record ComplianceReportData(Long laboratoryId, String laboratory, String from, String to,
                                   List<Deviation> deviations, String generatedBy, Instant generatedAt) {
    public ComplianceReportData { deviations = List.copyOf(deviations); }

    public record Deviation(Long id, Long equipmentId, Long batchId, String parameter,
                            Double value, Double threshold, String unit, String recordedAt,
                            String severity, String status, String resolution) {}
}
