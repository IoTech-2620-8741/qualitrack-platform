package com.iotech.qualitrack.platform.ra.infrastructure.documents;

import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.ComplianceReportData;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.EquipmentReportData;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Period-specific report contents using the same layout as batch reports. */
final class PeriodReportPdfRenderer extends AbstractReportPdfRenderer {
    byte[] render(ComplianceReportData data) {
        return renderDocument("Compliance review report", data.laboratory() + " / Laboratory #" + data.laboratoryId(), () -> {
            metrics(List.of(new Metric("DEVIATIONS", Integer.toString(data.deviations().size()), INK),
                    new Metric("NOT RESOLVED", Long.toString(data.deviations().stream().filter(a -> !"RESOLVED".equals(a.status())).count()), AMBER),
                    new Metric("CRITICAL", Long.toString(data.deviations().stream().filter(a -> "CRITICAL".equals(a.severity())).count()), RED),
                    new Metric("EQUIPMENT", Long.toString(data.deviations().stream().map(ComplianceReportData.Deviation::equipmentId).distinct().count()), TEAL)));
            section("01", "Review period");
            paragraph(date(data.from()) + " to " + date(data.to()) + " (inclusive, recorded calendar dates).", INK);
            paragraph("Metrics cover alerts recorded in this period. Alert states reflect the time of export; acknowledged alerts remain in the not-resolved count.", MUTED);
            section("02", "Deviation profile");
            if (data.deviations().isEmpty()) {
                paragraph("No deviation records in the selected period. No compliance percentage can be inferred from an empty register.", MUTED);
            } else {
                countChartPair("ALERTS BY CURRENT STATUS / COUNT", counts(data.deviations(), ComplianceReportData.Deviation::status),
                        "ALERTS BY SEVERITY / COUNT", counts(data.deviations(), ComplianceReportData.Deviation::severity));
                section("03", "Deviation register");
                var rows = data.deviations().stream().map(a -> List.of(
                        "Alert #" + a.id() + "\nEquipment #" + a.equipmentId() + "\nBatch: " + value(a.batchId()),
                        timestamp(a.recordedAt()), value(a.parameter()) + "\n" + number(a.value()) + " " + value(a.unit())
                                + "\nLimit: " + number(a.threshold()) + " " + value(a.unit()),
                        human(a.severity()) + "\n" + human(a.status()))).toList();
                table(List.of("Reference", "Recorded at", "Observation / limit", "Severity / state"), rows,
                        new float[]{.23f, .24f, .30f, .23f});
                for (var alert : data.deviations()) {
                    if (alert.resolution() != null && !alert.resolution().isBlank()) {
                        paragraph("Alert #" + alert.id() + " - Resolution: " + alert.resolution(), INK);
                    }
                }
            }
            section(data.deviations().isEmpty() ? "03" : "04", "Scope and authorship");
            paragraph("Recorded operational events; this report is not a regulatory certification. Counts do not demonstrate BPM compliance or measurement coverage.", MUTED);
            provenance(data.generatedBy(), data.generatedAt());
        });
    }

    byte[] render(EquipmentReportData data) {
        return renderDocument("Equipment activity report", data.name() + " / Equipment #" + data.equipmentId(), () -> {
            metrics(List.of(new Metric("AUDIT ENTRIES", Integer.toString(data.entries().size()), TEAL),
                    new Metric("MAINTENANCE", Integer.toString(data.maintenance().size()), INK),
                    new Metric("PARAMETERS", Integer.toString(data.parameters().size()), INK),
                    new Metric("ACTORS", Long.toString(data.entries().stream().map(EquipmentReportData.LogEntry::actorId).distinct().count()), INK)));
            section("01", "Equipment and period");
            table(List.of("Field", "Recorded value"), List.of(
                    List.of("Laboratory", value(data.laboratory())),
                    List.of("Type / model", value(data.type()) + " / " + value(data.model())),
                    List.of("Serial / sensor", value(data.serialNumber()) + " / " + value(data.sensorId())),
                    List.of("Current status", human(data.status())),
                    List.of("Activity period", date(data.from()) + " to " + date(data.to()) + " (inclusive)")), new float[]{.27f, .73f});
            section("02", "Current configured limits");
            paragraph("Configuration at export time, not a reconstruction of past settings or a measurement result.", MUTED);
            if (data.parameters().isEmpty()) paragraph("No parameter limits have been recorded for this equipment.", MUTED);
            else table(List.of("Parameter", "Minimum", "Maximum", "Unit"), data.parameters().stream().map(p ->
                    List.of(value(p.name()), number(p.minimum()), number(p.maximum()), value(p.unit()))).toList(),
                    new float[]{.40f, .20f, .20f, .20f});
            section("03", "Activity in the selected period");
            if (data.entries().isEmpty()) paragraph("No audit entries in the selected period.", MUTED);
            else countChart("AUDIT ACTIONS / COUNT", counts(data.entries(), EquipmentReportData.LogEntry::action), TEAL);
            if (!data.maintenance().isEmpty()) {
                countChart("MAINTENANCE TYPES / COUNT", counts(data.maintenance(), EquipmentReportData.Maintenance::type), AMBER);
            }
            section("04", "Maintenance register");
            if (data.maintenance().isEmpty()) paragraph("No maintenance records in the selected period.", MUTED);
            else table(List.of("Reference / date", "Type", "Technician", "Recorded intervention"), data.maintenance().stream().map(m ->
                    List.of("#" + m.id() + "\n" + date(m.date()), human(m.type()), value(m.technician()), value(m.description()))).toList(),
                    new float[]{.21f, .20f, .22f, .37f});
            section("05", "Audit trail");
            if (data.entries().isEmpty()) paragraph("No recorded actions to display.", MUTED);
            else table(List.of("ID / recorded at", "Action", "Actor", "Details"), data.entries().stream().map(e ->
                    List.of("#" + e.id() + "\n" + timestamp(e.recordedAt()), human(e.action()),
                            e.actorId() == null ? "System" : "User #" + e.actorId(), value(e.details()))).toList(),
                    new float[]{.26f, .18f, .16f, .40f});
            section("06", "Scope and authorship");
            paragraph("Activity and maintenance use the selected calendar dates. Equipment status and limits are current. Actor counts include System when recorded. Exporting this report creates a new audit entry after this snapshot.", MUTED);
            paragraph("No uptime, calibration validity or regulatory compliance score is inferred from these records. Telemetry measurements are not part of this log export.", MUTED);
            provenance(data.generatedBy(), data.generatedAt());
        });
    }

    private void provenance(String user, Instant generatedAt) throws IOException {
        paragraph("Generated by " + value(user) + " on " + timestamp(generatedAt.toString()), MUTED);
        paragraph("Timestamps without a zone retain their recorded local time. Records marked DEMO are synthetic training data, not production evidence.", MUTED);
    }

    private static <T> Map<String, Long> counts(List<T> records, Function<T, String> category) {
        return records.stream().collect(Collectors.groupingBy(category, TreeMap::new, Collectors.counting()));
    }
}
