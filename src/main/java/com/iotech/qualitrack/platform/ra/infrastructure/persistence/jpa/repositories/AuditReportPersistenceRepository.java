package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.AuditReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link AuditReportPersistenceEntity}.
 *
 * <p>Handles database operations for the audit_reports table using Long as
 * the primary key. Provides custom queries for reports by laboratory, batch,
 * equipment, and report type.</p>
 */
@Repository
public interface AuditReportPersistenceRepository extends JpaRepository<AuditReportPersistenceEntity, Long> {

    /**
     * Finds all generated reports associated with a laboratory.
     *
     * @param laboratoryId The numeric ID of the laboratory.
     */
    List<AuditReportPersistenceEntity> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Finds all generated reports associated with a production batch.
     *
     * @param batchId The numeric ID of the batch.
     */
    List<AuditReportPersistenceEntity> findAllByBatchId(Long batchId);

    /**
     * Finds all generated reports associated with an equipment.
     *
     * @param equipmentId The numeric ID of the equipment.
     */
    List<AuditReportPersistenceEntity> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds all generated reports of a specific type.
     *
     * @param reportType The report type.
     */
    List<AuditReportPersistenceEntity> findAllByReportType(ReportType reportType);
}