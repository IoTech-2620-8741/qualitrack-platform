package com.iotech.qualitrack.platform.ra.application.internal.outboundservices;

import java.time.Instant;
import java.util.List;

/** Current equipment configuration and dated activity records, kept as separate evidence. */
public record EquipmentReportData(Long equipmentId, String name, String laboratory, String type,
                                  String model, String serialNumber, String status, String sensorId,
                                  String from, String to, List<Parameter> parameters,
                                  List<Maintenance> maintenance, List<LogEntry> entries,
                                  String generatedBy, Instant generatedAt) {
    public EquipmentReportData {
        parameters = List.copyOf(parameters);
        maintenance = List.copyOf(maintenance);
        entries = List.copyOf(entries);
    }

    public record Parameter(String name, Double minimum, Double maximum, String unit) {}
    public record Maintenance(Long id, String date, String type, String technician, String description) {}
    public record LogEntry(Long id, String recordedAt, String action, Long actorId, String details) {}
}
