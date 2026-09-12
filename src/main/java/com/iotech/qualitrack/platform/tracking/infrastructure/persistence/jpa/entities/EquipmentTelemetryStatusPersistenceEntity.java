package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for current equipment telemetry status records.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipment_telemetry_statuses")
public class EquipmentTelemetryStatusPersistenceEntity extends AuditableAbstractPersistenceEntity {
    /**
     * Equipment associated with this telemetry status.
     */
    @Column(nullable = false)
    private Long equipmentId;

    /**
     * Indicates whether the equipment is currently online.
     */
    @Column(nullable = false)
    private Boolean isOnline;

    /**
     * Current evaluated telemetry status.
     *
     * @remarks
     * Converted automatically by TelemetryStatusPersistenceConverter.
     */
    @Column(nullable = false, length = 40)
    private TelemetryStatus currentStatus;

    /**
     * Timestamp of the latest received heartbeat.
     */
    @Column(nullable = false, length = 50)
    private String lastHeartbeat;
}