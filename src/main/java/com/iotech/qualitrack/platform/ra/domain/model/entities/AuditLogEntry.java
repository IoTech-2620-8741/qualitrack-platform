package com.iotech.qualitrack.platform.ra.domain.model.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain entity representing an immutable audit log entry.
 *
 * <p>Audit log entries provide traceability for relevant user or system actions
 * performed over domain entities such as equipment, batches, reports, alerts,
 * or laboratory records.</p>
 */
@Getter
public class AuditLogEntry {

    /**
     * The unique numeric identifier of the audit log entry.
     */
    private Long id;

    /**
     * The action that was performed.
     */
    private AuditAction action;

    /**
     * The type of domain entity affected by the action.
     */
    private String entityType;

    /**
     * The numeric identifier of the affected entity, if applicable.
     */
    private Long entityId;

    /**
     * The numeric identifier of the user or system agent who performed the action.
     */
    private Long performedBy;

    /**
     * Timestamp when the action occurred.
     */
    private String timestamp;

    /**
     * Additional details or serialized context for the action.
     */
    private String details;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public AuditLogEntry() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs an AuditLogEntry entity from persistence data.
     *
     * @param id The numeric audit log identifier.
     * @param action The action performed.
     * @param entityType The affected entity type.
     * @param entityId The affected entity identifier.
     * @param performedBy The user or system actor identifier.
     * @param timestamp The occurrence timestamp.
     * @param details Additional action details.
     */
    public AuditLogEntry(
            Long id,
            AuditAction action,
            String entityType,
            Long entityId,
            Long performedBy,
            String timestamp,
            String details
    ) {
        this.id = id;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
    }

    /**
     * Records a new audit log entry.
     *
     * @param action The action performed.
     * @param entityType The affected entity type.
     * @param entityId The affected entity identifier.
     * @param performedBy The user or system actor identifier.
     * @param details Additional action details.
     */
    public AuditLogEntry(
            AuditAction action,
            String entityType,
            Long entityId,
            Long performedBy,
            String details
    ) {
        this.action = Objects.requireNonNull(action, "action cannot be null");
        this.entityType = Objects.requireNonNull(entityType, "entityType cannot be null");
        this.entityId = entityId;
        if (performedBy != null && performedBy <= 0) throw new IllegalArgumentException("Invalid audit actor");
        this.performedBy = performedBy;
        this.details = details;
        this.timestamp = LocalDateTime.now().toString();
    }
}
