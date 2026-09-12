package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the equipment telemetry aggregate root.
 *
 * @remarks
 * The aggregate row represents the monitored equipment boundary. Measurements,
 * status records, and history points are persisted in dedicated tables and are
 * associated through the equipment identifier.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipment_telemetry")
public class EquipmentTelemetryPersistenceEntity extends AuditableAbstractPersistenceEntity {
    /**
     * Equipment represented by this telemetry aggregate.
     */
    @Column(nullable = false, unique = true)
    private Long equipmentId;
}