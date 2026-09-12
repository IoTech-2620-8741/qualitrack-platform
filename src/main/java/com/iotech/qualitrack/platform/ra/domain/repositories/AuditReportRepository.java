package com.iotech.qualitrack.platform.ra.domain.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for generated audit reports.
 *
 * <p>Defines the persistence contract for immutable report records generated
 * by the Reporting and Analysis bounded context.</p>
 */
public interface AuditReportRepository {

    /**
     * Finds an audit report by its unique numeric identifier.
     *
     * @param id The report identifier.
     * @return The matching audit report, if found.
     */
    Optional<AuditReport> findById(Long id);

    /**
     * Finds all generated reports associated with a laboratory.
     *
     * @param laboratoryId The laboratory identifier.
     * @return List of generated audit reports.
     */
    List<AuditReport> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Finds all generated reports associated with a production batch.
     *
     * @param batchId The batch identifier.
     * @return List of generated audit reports.
     */
    List<AuditReport> findAllByBatchId(Long batchId);

    /**
     * Finds all generated reports associated with an equipment.
     *
     * @param equipmentId The equipment identifier.
     * @return List of generated audit reports.
     */
    List<AuditReport> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds all generated reports by report type.
     *
     * @param reportType The report type.
     * @return List of generated audit reports.
     */
    List<AuditReport> findAllByReportType(ReportType reportType);

    /**
     * Saves a generated audit report.
     *
     * @param auditReport The report aggregate to persist.
     * @return The persisted audit report aggregate.
     */
    AuditReport save(AuditReport auditReport);

    /**
     * Checks whether a report exists by its unique numeric identifier.
     *
     * @param id The report identifier.
     * @return true if the report exists; otherwise false.
     */
    boolean existsById(Long id);
}