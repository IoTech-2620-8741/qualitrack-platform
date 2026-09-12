package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a KPI metric stored under a dashboard snapshot.
 */
@Entity
@Table(name = "kpi_metrics")
@Getter
@Setter
@NoArgsConstructor
public class KpiMetricPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "dashboard_id", nullable = false)
    private Long dashboardId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(name = "target_value")
    private Double targetValue;

    /**
     * Converted automatically by KpiMetricStatusPersistenceConverter.
     */
    @Column(nullable = false, length = 50)
    private KpiMetricStatus status;

    @Column(name = "recorded_at", nullable = false, length = 30)
    private String recordedAt;
}
