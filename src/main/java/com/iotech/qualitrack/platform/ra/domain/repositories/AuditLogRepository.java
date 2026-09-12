package com.iotech.qualitrack.platform.ra.domain.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for audit log entries.
 *
 * <p>Audit log entries are append-only records used to preserve traceability
 * of relevant user and system actions.</p>
 */
public interface AuditLogRepository {

    /**
     * Finds an audit log entry by its unique numeric identifier.
     *
     * @param id The audit log entry identifier.
     * @return The matching audit log entry, if found.
     */
    Optional<AuditLogEntry> findById(Long id);

    /**
     * Finds all audit log entries.
     *
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAll();

    /**
     * Finds all audit log entries related to a specific equipment.
     *
     * @param equipmentId The equipment identifier.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds all audit log entries related to a specific production batch.
     *
     * @param batchId The batch identifier.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByBatchId(Long batchId);

    /**
     * Finds all audit log entries performed by a specific actor.
     *
     * @param performedBy The actor identifier.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByPerformedBy(Long performedBy);

    /**
     * Finds all audit log entries by action.
     *
     * @param action The audit action.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByAction(AuditAction action);

    /**
     * Finds all audit log entries within a timestamp range.
     *
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByDateRange(String dateFrom, String dateTo);

    /**
     * Finds all audit log entries related to equipment within a timestamp range.
     *
     * @param equipmentId The equipment identifier.
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByEquipmentIdAndDateRange(Long equipmentId, String dateFrom, String dateTo);

    /**
     * Finds all audit log entries related to a batch within a timestamp range.
     *
     * @param batchId The batch identifier.
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     * @return List of audit log entries.
     */
    List<AuditLogEntry> findAllByBatchIdAndDateRange(Long batchId, String dateFrom, String dateTo);

    /**
     * Saves an audit log entry.
     *
     * @param auditLogEntry The audit log entry to persist.
     * @return The persisted audit log entry.
     */
    AuditLogEntry save(AuditLogEntry auditLogEntry);

    /**
     * Checks whether an audit log entry exists by its unique numeric identifier.
     *
     * @param id The audit log entry identifier.
     * @return true if the audit log entry exists; otherwise false.
     */
    boolean existsById(Long id);
}