package com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.RawMaterialUsageRepository;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.BatchReportData;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.ComplianceReportData;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.EquipmentReportData;
import com.iotech.qualitrack.platform.equipment.domain.repositories.MaintenanceRepository;
import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import com.iotech.qualitrack.platform.ca.domain.repositories.DeviationAlertRepository;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;
import com.iotech.qualitrack.platform.equipment.domain.repositories.BpmParameterConfigRepository;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.entities.KpiMetric;
import com.iotech.qualitrack.platform.ra.domain.model.entities.TrendDataPoint;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Reads operational records without manufacturing observations or regulatory targets. */
@Service
@Transactional(readOnly = true)
public class RaOperationalDataService {
    private final EquipmentRepository equipment;
    private final BatchRepository batches;
    private final DeviationAlertRepository alerts;
    private final MeasurementRepository measurements;
    private final BpmParameterConfigRepository configurations;
    private final RawMaterialUsageRepository materialUsages;
    private final LaboratoryRepository laboratories;
    private final MaintenanceRepository maintenance;

    public RaOperationalDataService(EquipmentRepository equipment, BatchRepository batches,
            DeviationAlertRepository alerts, MeasurementRepository measurements,
            BpmParameterConfigRepository configurations, RawMaterialUsageRepository materialUsages,
            LaboratoryRepository laboratories, MaintenanceRepository maintenance) {
        this.equipment = equipment;
        this.batches = batches;
        this.alerts = alerts;
        this.measurements = measurements;
        this.configurations = configurations;
        this.materialUsages = materialUsages;
        this.laboratories = laboratories;
        this.maintenance = maintenance;
    }

    public BatchReportData batchReport(Batch batch, List<DeviationAlert> recordedAlerts,
            boolean includeDeviations, String generatedBy, java.time.Instant generatedAt) {
        var laboratory = laboratories.findById(batch.getLabId()).orElseThrow();
        var materials = materialUsages.findAllByBatchId(batch.getId()).stream()
                .map(item -> new BatchReportData.Material(item.getRawMaterialId(), item.getRawMaterialName(),
                        item.getQuantityUsed(), item.getUnit(), item.getUsageDate())).toList();
        var deviations = includeDeviations ? recordedAlerts.stream()
                .map(item -> new BatchReportData.Deviation(item.getId(), item.getEquipmentId(), item.getParameterName(),
                        item.getRecordedValue(), item.getThresholdValue(), item.getUnit(), item.getTimestamp(),
                        item.getSeverity().name(), item.getStatus().name(), item.getResolutionNotes())).toList()
                : List.<BatchReportData.Deviation>of();
        return new BatchReportData(laboratory.getName().name(), batch.getId(), batch.getBatchNumber(),
                batch.getProductName(), batch.getQuantity(), batch.getUnit(), batch.getStatus().name(),
                batch.getStartDate(), batch.getEndDate(), batch.getNotes(), materials, includeDeviations,
                deviations, generatedBy, generatedAt);
    }

    public ComplianceReportData complianceReport(Long laboratoryId, String from, String to,
            List<DeviationAlert> recordedAlerts, String generatedBy, java.time.Instant generatedAt) {
        var rows = recordedAlerts.stream().sorted(Comparator.comparing(DeviationAlert::getTimestamp))
                .map(item -> new ComplianceReportData.Deviation(item.getId(), item.getEquipmentId(), item.getBatchId(),
                        item.getParameterName(), item.getRecordedValue(), item.getThresholdValue(), item.getUnit(),
                        item.getTimestamp(), item.getSeverity().name(), item.getStatus().name(), item.getResolutionNotes())).toList();
        return new ComplianceReportData(laboratoryId, laboratories.findById(laboratoryId).orElseThrow().getName().name(),
                from, to, rows, generatedBy, generatedAt);
    }

    public EquipmentReportData equipmentReport(Equipment device, String from, String to,
            List<AuditLogEntry> recordedEntries, String generatedBy, java.time.Instant generatedAt) {
        var start = java.time.LocalDate.parse(from.substring(0, 10));
        var end = java.time.LocalDate.parse(to.substring(0, 10));
        var parameters = configurations.findAllByEquipmentId(device.getId()).stream()
                .map(item -> new EquipmentReportData.Parameter(item.getParameterName().name(), item.getMinValue(),
                        item.getMaxValue(), item.getUnit())).toList();
        var interventions = maintenance.findAllByEquipmentId(device.getId()).stream()
                .filter(item -> item.getMaintenanceDate() != null && !item.getMaintenanceDate().isBefore(start)
                        && !item.getMaintenanceDate().isAfter(end))
                .sorted(Comparator.comparing(item -> item.getMaintenanceDate()))
                .map(item -> new EquipmentReportData.Maintenance(item.getId(), item.getMaintenanceDate().toString(),
                        item.getType().name(), item.getTechnicianName(), item.getDescription())).toList();
        var entries = recordedEntries.stream().sorted(Comparator.comparing(AuditLogEntry::getTimestamp))
                .map(item -> new EquipmentReportData.LogEntry(item.getId(), item.getTimestamp(), item.getAction().name(),
                        item.getPerformedBy(), item.getDetails())).toList();
        return new EquipmentReportData(device.getId(), device.getName(),
                laboratories.findById(device.getLabId()).orElseThrow().getName().name(), device.getType().name(),
                device.getModel(), device.getSerialNumber(), device.getStatus().name(),
                device.getSensorExternalId() == null ? null : device.getSensorExternalId().value(), from, to,
                parameters, interventions, entries, generatedBy, generatedAt);
    }

    public KpiDashboard dashboard(Long laboratoryId) {
        var devices = equipment.findAllByLabId(laboratoryId);
        var lots = batches.findAllByLabId(laboratoryId);
        var metrics = new ArrayList<KpiMetric>();
        var recordedAt = LocalDateTime.now().toString();
        if (!devices.isEmpty()) {
            metrics.add(count("equipment-count", devices.size(), recordedAt));
            var recordedAlerts = devices.stream()
                    .flatMap(device -> alerts.findAllByEquipmentId(device.getId()).stream()).toList();
            if (!recordedAlerts.isEmpty()) {
                metrics.add(count("unresolved-alert-count", recordedAlerts.stream()
                        .filter(alert -> alert.getStatus() != AlertStatus.RESOLVED).count(), recordedAt));
            }
        }
        if (!lots.isEmpty()) {
            metrics.add(count("batch-count", lots.size(), recordedAt));
            metrics.add(count("released-batch-count", lots.stream()
                    .filter(batch -> batch.getStatus() == BatchStatus.RELEASED).count(), recordedAt));
        }
        return new KpiDashboard(null, laboratoryId, recordedAt, null, List.copyOf(metrics));
    }

    private KpiMetric count(String name, long value, String at) {
        return new KpiMetric(null, name, (double) value, "count", null, KpiMetricStatus.UNKNOWN, at);
    }

    public List<DeviationTrend> trends(Long equipmentId) {
        return configurations.findAllByEquipmentId(equipmentId).stream()
                .map(config -> trend(equipmentId, config.getParameterName().name()))
                .filter(trend -> !trend.getDataPoints().isEmpty()).toList();
    }

    public DeviationTrend trend(Long equipmentId, String parameter) {
        var config = configurations.findAllByEquipmentId(equipmentId).stream()
                .filter(item -> item.getParameterName().name().equalsIgnoreCase(parameter)).findFirst();
        if (config.isEmpty()) return new DeviationTrend(parameter, equipmentId, List.of());
        var limits = config.get();
        var points = measurements.findLatestByEquipmentId(equipmentId).stream()
                .filter(item -> item.getParameterName().equalsIgnoreCase(parameter))
                .filter(item -> item.getUnit().equalsIgnoreCase(limits.getUnit()))
                .filter(item -> item.getValue() != null && Double.isFinite(item.getValue()))
                .sorted(Comparator.comparing(Measurement::getTimestamp))
                .map(item -> new TrendDataPoint(item.getTimestamp(), item.getValue(),
                        limits.getMaxValue(), limits.getMinValue())).toList();
        return new DeviationTrend(parameter, equipmentId, points);
    }

    public Batch batch(Long id) {
        return batches.findById(id).orElseThrow(() -> new IllegalArgumentException("Batch does not exist"));
    }

    public Equipment equipment(Long id) {
        return equipment.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment does not exist"));
    }

    public List<DeviationAlert> batchAlerts(Long id) { return alerts.findAllByBatchId(id); }

    public List<DeviationAlert> laboratoryAlerts(Long id) {
        return equipment.findAllByLabId(id).stream()
                .flatMap(device -> alerts.findAllByEquipmentId(device.getId()).stream()).toList();
    }
}
