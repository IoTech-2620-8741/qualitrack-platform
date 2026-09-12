package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for telemetry measurements.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telemetry_measurements")
public class MeasurementPersistenceEntity extends AuditableAbstractPersistenceEntity {
    /**
     * Equipment that produced the measurement.
     */
    @Column(nullable = false)
    private Long equipmentId;

    /**
     * Name of the monitored telemetry parameter.
     */
    @Column(nullable = false, length = 120)
    private String parameterName;

    /**
     * Recorded telemetry value.
     */
    @Column(nullable = false)
    private Double value;

    /**
     * Measurement unit.
     */
    @Column(nullable = false, length = 40)
    private String unit;

    /**
     * Timestamp when the measurement was captured by the source equipment.
     */
    @Column(nullable = false, length = 50)
    private String timestamp;
}