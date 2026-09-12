package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a deviation alert table.
 *
 * <p>This entity stores alerts generated when a monitored process parameter
 * exceeds its allowed threshold.</p>
 */
@Entity
@Table(name = "deviation_alerts")
@Getter
@Setter
@NoArgsConstructor
public class DeviationAlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "parameter_name", nullable = false, length = 100)
    private String parameterName;

    @Column(name = "recorded_value", nullable = false)
    private Double recordedValue;

    @Column(name = "threshold_value", nullable = false)
    private Double thresholdValue;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false, length = 30)
    private String timestamp;

    /**
     * Converted automatically by AlertSeverityPersistenceConverter.
     */
    @Column(nullable = false, length = 50)
    private AlertSeverity severity;

    /**
     * Converted automatically by AlertStatusPersistenceConverter.
     */
    @Column(nullable = false, length = 50)
    private AlertStatus status;

    @Column(name = "acknowledged_by")
    private Long acknowledgedBy;

    @Column(name = "resolved_by")
    private Long resolvedBy;

    @Column(name = "resolution_notes", length = 500)
    private String resolutionNotes;
}