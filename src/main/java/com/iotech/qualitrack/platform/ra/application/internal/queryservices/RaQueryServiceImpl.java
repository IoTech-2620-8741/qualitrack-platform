package com.iotech.qualitrack.platform.ra.application.internal.queryservices;

import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditLogQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportByIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportsByBatchIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportsByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetDeviationTrendsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetKpiDashboardByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditLogRepository;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditReportRepository;
import com.iotech.qualitrack.platform.ra.domain.repositories.DeviationTrendRepository;
import com.iotech.qualitrack.platform.ra.domain.repositories.KpiDashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that executes Reporting and Analysis queries.
 *
 * <p>This service coordinates read-side use cases for KPI dashboard snapshots,
 * deviation trend analyses, audit logs, and generated audit reports.</p>
 */
@Service
public class RaQueryServiceImpl implements RaQueryService {
    private final com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl.RaOperationalDataService data;

    private final KpiDashboardRepository kpiDashboardRepository;
    private final DeviationTrendRepository deviationTrendRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuditReportRepository auditReportRepository;

    public RaQueryServiceImpl(
            KpiDashboardRepository kpiDashboardRepository,
            DeviationTrendRepository deviationTrendRepository,
            AuditLogRepository auditLogRepository,
            AuditReportRepository auditReportRepository,
            com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl.RaOperationalDataService data
    ) {
        this.kpiDashboardRepository = kpiDashboardRepository;
        this.deviationTrendRepository = deviationTrendRepository;
        this.auditLogRepository = auditLogRepository;
        this.auditReportRepository = auditReportRepository;
        this.data = data;
    }

    /**
     * Handles retrieval of the latest KPI dashboard snapshot for a laboratory.
     *
     * @param query The query containing the laboratory identifier.
     * @return The latest KPI dashboard snapshot, if found.
     */
    @Override
    public Optional<KpiDashboard> handle(GetKpiDashboardByLaboratoryIdQuery query) {
        return Optional.of(data.dashboard(query.laboratoryId()));
    }

    /**
     * Handles retrieval of deviation trends for an equipment.
     *
     * @param query The query containing the equipment identifier.
     * @return List of deviation trends associated with the equipment.
     */
    @Override
    public List<DeviationTrend> handle(GetDeviationTrendsByEquipmentIdQuery query) {
        return data.trends(query.equipmentId());
    }

    /**
     * Handles retrieval of audit log entries using optional filters.
     *
     * @param query The query containing optional equipment, batch, and date filters.
     * @return List of audit log entries matching the provided filters.
     */
    @Override
    public List<AuditLogEntry> handle(GetAuditLogQuery query) {
        var hasEquipmentFilter = query.equipmentId() != null;
        var hasBatchFilter = query.batchId() != null;
        var hasDateRange = hasValidDateRange(query.dateFrom(), query.dateTo());

        if (hasEquipmentFilter && hasDateRange) {
            return auditLogRepository.findAllByEquipmentIdAndDateRange(
                    query.equipmentId(),
                    query.dateFrom(),
                    query.dateTo()
            );
        }

        if (hasBatchFilter && hasDateRange) {
            return auditLogRepository.findAllByBatchIdAndDateRange(
                    query.batchId(),
                    query.dateFrom(),
                    query.dateTo()
            );
        }

        if (hasEquipmentFilter) {
            return auditLogRepository.findAllByEquipmentId(query.equipmentId());
        }

        if (hasBatchFilter) {
            return auditLogRepository.findAllByBatchId(query.batchId());
        }

        if (hasDateRange) {
            return auditLogRepository.findAllByDateRange(query.dateFrom(), query.dateTo());
        }

        return auditLogRepository.findAll();
    }

    /**
     * Handles retrieval of an audit report by its unique identifier.
     *
     * @param query The query containing the report identifier.
     * @return The matching audit report, if found.
     */
    @Override
    public Optional<AuditReport> handle(GetAuditReportByIdQuery query) {
        return auditReportRepository.findById(query.reportId());
    }

    /**
     * Handles retrieval of generated audit reports for a laboratory.
     *
     * @param query The query containing the laboratory identifier.
     * @return List of generated audit reports associated with the laboratory.
     */
    @Override
    public List<AuditReport> handle(GetAuditReportsByLaboratoryIdQuery query) {
        return auditReportRepository.findAllByLaboratoryId(query.laboratoryId());
    }

    /**
     * Handles retrieval of generated audit reports for a production batch.
     *
     * @param query The query containing the batch identifier.
     * @return List of generated audit reports associated with the batch.
     */
    @Override
    public List<AuditReport> handle(GetAuditReportsByBatchIdQuery query) {
        return auditReportRepository.findAllByBatchId(query.batchId());
    }

    /**
     * Handles retrieval of generated audit reports for an equipment.
     *
     * @param query The query containing the equipment identifier.
     * @return List of generated audit reports associated with the equipment.
     */
    @Override
    public List<AuditReport> handle(GetAuditReportsByEquipmentIdQuery query) {
        return auditReportRepository.findAllByEquipmentId(query.equipmentId());
    }

    /**
     * Determines whether a date range filter is complete.
     *
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     * @return true if both date bounds are present and not blank.
     */
    private boolean hasValidDateRange(String dateFrom, String dateTo) {
        return dateFrom != null && !dateFrom.isBlank()
                && dateTo != null && !dateTo.isBlank();
    }
}
