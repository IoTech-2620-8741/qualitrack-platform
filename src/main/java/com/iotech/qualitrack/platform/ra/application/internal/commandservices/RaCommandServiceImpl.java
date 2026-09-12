package com.iotech.qualitrack.platform.ra.application.internal.commandservices;

import com.iotech.qualitrack.platform.iam.interfaces.acl.IamContextFacade;
import com.iotech.qualitrack.platform.ra.application.commandservices.RaCommandService;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.ReportDocumentWriter;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl.RaOperationalDataService;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.commands.*;
import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.*;
import com.iotech.qualitrack.platform.ra.domain.repositories.*;
import com.iotech.qualitrack.platform.shared.application.result.*;
import com.iotech.qualitrack.platform.shared.application.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.BiFunction;

@Service
@Transactional
public class RaCommandServiceImpl implements RaCommandService {
    private final AuditReportRepository reports;
    private final ReportDocumentRepository documents;
    private final AuditLogRepository audit;
    private final KpiDashboardRepository dashboards;
    private final DeviationTrendRepository trends;
    private final RaOperationalDataService data;
    private final ReportDocumentWriter writer;
    private final CurrentUser currentUser;
    private final IamContextFacade users;

    public RaCommandServiceImpl(AuditReportRepository reports, AuditLogRepository audit,
            KpiDashboardRepository dashboards, DeviationTrendRepository trends,
            RaOperationalDataService data, ReportDocumentWriter writer, CurrentUser currentUser,
            IamContextFacade users, ReportDocumentRepository documents) {
        this.reports = reports;
        this.documents = documents;
        this.audit = audit;
        this.dashboards = dashboards;
        this.trends = trends;
        this.data = data;
        this.writer = writer;
        this.currentUser = currentUser;
        this.users = users;
    }

    @Override
    public Result<KpiDashboard, ApplicationError> handle(CalculateKpiDashboardCommand command) {
        return execute("KpiDashboard", () -> {
            var dashboard = dashboards.save(data.dashboard(command.laboratoryId()));
            record(AuditAction.GENERATE, "KPI_DASHBOARD", dashboard.getId(), "Operational counts calculated");
            return dashboard;
        });
    }

    @Override
    public Result<DeviationTrend, ApplicationError> handle(CalculateDeviationTrendCommand command) {
        return execute("DeviationTrend", () -> {
            var trend = data.trend(command.equipmentId(), command.parameterName());
            if (trend.getDataPoints().isEmpty()) {
                throw new IllegalArgumentException("No measurements with matching units and configured limits");
            }
            var saved = trends.save(trend);
            record(AuditAction.GENERATE, "DEVIATION_TREND", saved.getId(),
                    "Measurements compared with the current configured limits");
            return saved;
        });
    }

    @Override
    public Result<byte[], ApplicationError> handle(GenerateBatchReportCommand command) {
        return execute("BatchReport", () -> {
            var batch = data.batch(command.batchId());
            var recordedAlerts = command.includeDeviations() ? data.batchAlerts(batch.getId())
                    : List.<com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert>of();
            var rows = new ArrayList<List<String>>();
            row(rows, "Batch ID", batch.getId());
            row(rows, "Batch number", batch.getBatchNumber());
            row(rows, "Product", batch.getProductName());
            row(rows, "Quantity", batch.getQuantity() + " " + batch.getUnit());
            row(rows, "Status", batch.getStatus());
            row(rows, "Started", batch.getStartDate());
            row(rows, "Finished", batch.getEndDate());
            row(rows, "Notes", batch.getNotes());
            if (command.includeDeviations()) {
                row(rows, "Recorded deviations", recordedAlerts.size());
                recordedAlerts.forEach(alert -> row(rows, "Alert " + alert.getId(),
                        alert.getTimestamp() + " | " + alert.getParameterName() + " | "
                        + alert.getRecordedValue() + " " + alert.getUnit() + " | " + alert.getStatus()));
            }
            if (command.includeTelemetry()) {
                row(rows, "Telemetry availability",
                        "Not available: batches do not yet have an equipment/measurement association.");
            }
            return report(ReportType.BATCH_TRACEABILITY, command.format(), batch.getLabId(),
                    batch.getId(), null, command.requestedBy(), batch.getStartDate(), batch.getEndDate(), rows,
                    command.format() == ReportFormat.PDF ? (name, at) -> writer.writeBatchPdf(
                            data.batchReport(batch, recordedAlerts, command.includeDeviations(), name, at)) : null);
        });
    }

    @Override
    public Result<byte[], ApplicationError> handle(GenerateComplianceReportCommand command) {
        return execute("ComplianceReport", () -> {
            validatePeriod(command.startDate(), command.endDate());
            var alerts = data.laboratoryAlerts(command.laboratoryId()).stream()
                    .filter(alert -> inPeriod(alert.getTimestamp(), command.startDate(), command.endDate())).toList();
            return report(ReportType.COMPLIANCE_PERIOD, command.format(), command.laboratoryId(),
                    null, null, command.requestedBy(), command.startDate(), command.endDate(), new ArrayList<>(),
                    (name, at) -> writer.writeCompliance(command.format(), data.complianceReport(command.laboratoryId(),
                            command.startDate(), command.endDate(), alerts, name, at)));
        });
    }

    @Override
    public Result<byte[], ApplicationError> handle(ExportEquipmentLogCommand command) {
        return execute("EquipmentLog", () -> {
            validatePeriod(command.startDate(), command.endDate());
            var device = data.equipment(command.equipmentId());
            var entries = audit.findAllByEquipmentId(device.getId()).stream()
                    .filter(entry -> inPeriod(entry.getTimestamp(), command.startDate(), command.endDate())).toList();
            return report(ReportType.EQUIPMENT_LOG, command.format(), device.getLabId(),
                    null, device.getId(), command.requestedBy(), command.startDate(), command.endDate(), new ArrayList<>(),
                    (name, at) -> writer.writeEquipment(command.format(), data.equipmentReport(device,
                            command.startDate(), command.endDate(), entries, name, at)));
        });
    }

    private byte[] report(ReportType type, ReportFormat format, Long laboratoryId, Long batchId,
            Long equipmentId, Long requestedBy, String from, String to, List<List<String>> rows) {
        return report(type, format, laboratoryId, batchId, equipmentId, requestedBy, from, to, rows, null);
    }

    private byte[] report(ReportType type, ReportFormat format, Long laboratoryId, Long batchId,
            Long equipmentId, Long requestedBy, String from, String to, List<List<String>> rows,
            BiFunction<String, Instant, byte[]> customRenderer) {
        var actor = currentUser.userId();
        if (actor == null || !actor.equals(requestedBy)) {
            throw new org.springframework.security.access.AccessDeniedException("Invalid report requester");
        }
        var generatedAt = Instant.now();
        row(rows, "Generated at", generatedAt);
        var name = users.getUsernameByUserId(actor);
        row(rows, "Generated by", name);
        var bytes = customRenderer == null ? writer.write(format, "QualiTrack - " + type, rows)
                : customRenderer.apply(name, generatedAt);
        var report = reports.save(new AuditReport(laboratoryId, batchId, equipmentId, actor, name, type, from, to,
                "report-" + UUID.randomUUID() + "." + format.name().toLowerCase(java.util.Locale.ROOT), bytes));
        documents.save(new ReportDocument(report.getId(), format, bytes));
        record(AuditAction.GENERATE, equipmentId != null ? "EQUIPMENT" : batchId != null ? "BATCH" : "LABORATORY",
                equipmentId != null ? equipmentId : batchId != null ? batchId : laboratoryId,
                "Report generated from persisted operational records: " + type);
        return bytes;
    }

    private void record(AuditAction action, String type, Long id, String details) {
        audit.save(new AuditLogEntry(action, type, id, currentUser.userId(), details));
    }

    private void row(List<List<String>> rows, String label, Object value) {
        rows.add(List.of(label, value == null ? "Not recorded" : value.toString()));
    }

    private static void validatePeriod(String from, String to) {
        if (date(from).isAfter(date(to))) throw new IllegalArgumentException("Start date must not exceed end date");
    }

    private static LocalDate date(String value) {
        if (value == null || value.length() < 10) throw new IllegalArgumentException("An ISO date is required");
        return LocalDate.parse(value.substring(0, 10));
    }

    private static boolean inPeriod(String timestamp, String from, String to) {
        if (timestamp == null) return false;
        var observed = date(timestamp);
        return !observed.isBefore(date(from)) && !observed.isAfter(date(to));
    }

    private <T> Result<T, ApplicationError> execute(String entity, Supplier<T> operation) {
        try { return Result.success(operation.get()); }
        catch (IllegalArgumentException exception) {
            return Result.failure(ApplicationError.validationError(entity, exception.getMessage()));
        }
    }
}
