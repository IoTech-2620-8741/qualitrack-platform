package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing one measurement point in a deviation trend.
 */
@Entity
@Table(name = "trend_data_points")
@Getter
@Setter
@NoArgsConstructor
public class TrendDataPointPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "trend_id", nullable = false)
    private Long trendId;

    @Column(nullable = false, length = 30)
    private String timestamp;

    @Column(name = "recorded_value", nullable = false)
    private Double recordedValue;

    @Column(name = "upper_threshold", nullable = false)
    private Double upperThreshold;

    @Column(name = "lower_threshold", nullable = false)
    private Double lowerThreshold;
}