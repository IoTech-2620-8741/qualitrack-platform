package com.iotech.qualitrack.platform.ra.application.internal.outboundservices;

import java.time.Instant;
import java.util.List;

/** Immutable, presentation-neutral snapshot of records linked to one production batch. */
public record BatchReportData(
        String laboratory, Long batchId, String batchNumber, String product,
        Double quantity, String unit, String status, String started, String finished, String notes,
        List<Material> materials, boolean deviationsIncluded, List<Deviation> deviations,
        String generatedBy, Instant generatedAt
) {
    public BatchReportData {
        materials = List.copyOf(materials);
        deviations = List.copyOf(deviations);
    }

    public record Material(Long id, String name, Double quantity, String unit, String usedOn) {}

    public record Deviation(Long id, Long equipmentId, String parameter, Double value,
                            Double threshold, String unit, String recordedAt, String severity,
                            String status, String resolution) {}
}
