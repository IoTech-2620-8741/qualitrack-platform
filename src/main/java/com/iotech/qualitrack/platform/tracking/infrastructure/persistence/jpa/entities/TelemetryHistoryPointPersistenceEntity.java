package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for historical telemetry points.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telemetry_history_points")
public class TelemetryHistoryPointPersistenceEntity extends AuditableAbstractPersistenceEntity {
    /**
     * Equipment that produced the historical telemetry point.
     */
    @Column(nullable = false)
    private Long equipmentId;

    /**
     * Name of the monitored telemetry parameter.
     */
    @Column(nullable = false, length = 120)
    private String parameterName;

    /**
     * Recorded value for the historical telemetry point.
     */
    @Column(nullable = false)
    private Double recordedValue;

    /**
     * Timestamp when the telemetry point was captured.
     */
    @Column(nullable = false, length = 50)
    private String timestamp;

    /**
     * Indicates whether the telemetry point represents an anomaly.
     */
    @Column(nullable = false)
    private Boolean isAnomaly;
}