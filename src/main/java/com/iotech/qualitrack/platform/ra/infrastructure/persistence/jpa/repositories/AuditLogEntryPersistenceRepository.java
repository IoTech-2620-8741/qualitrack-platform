package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.AuditLogEntryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link AuditLogEntryPersistenceEntity}.
 *
 * <p>Handles database operations for append-only audit log entries.</p>
 */
@Repository
public interface AuditLogEntryPersistenceRepository extends JpaRepository<AuditLogEntryPersistenceEntity, Long> {

    /**
     * Finds all audit log entries associated with a specific entity type and entity id.
     *
     * @param entityType The affected entity type.
     * @param entityId The affected entity ID.
     */
    List<AuditLogEntryPersistenceEntity> findAllByEntityTypeAndEntityId(String entityType, Long entityId);

    /**
     * Finds all audit log entries performed by a specific actor.
     *
     * @param performedBy The actor ID.
     */
    List<AuditLogEntryPersistenceEntity> findAllByPerformedBy(Long performedBy);

    /**
     * Finds all audit log entries by action.
     *
     * @param action The audit action.
     */
    List<AuditLogEntryPersistenceEntity> findAllByAction(AuditAction action);

    /**
     * Finds all audit log entries within a timestamp range.
     *
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     */
    List<AuditLogEntryPersistenceEntity> findAllByTimestampBetween(String dateFrom, String dateTo);

    /**
     * Finds all audit log entries for an entity within a timestamp range.
     *
     * @param entityType The affected entity type.
     * @param entityId The affected entity ID.
     * @param dateFrom Lower timestamp bound.
     * @param dateTo Upper timestamp bound.
     */
    List<AuditLogEntryPersistenceEntity> findAllByEntityTypeAndEntityIdAndTimestampBetween(
            String entityType,
            Long entityId,
            String dateFrom,
            String dateTo
    );
}