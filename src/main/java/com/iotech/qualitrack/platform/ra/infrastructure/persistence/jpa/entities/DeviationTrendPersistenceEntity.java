package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a deviation trend analysis.
 */
@Entity
@Table(name = "deviation_trends")
@Getter
@Setter
@NoArgsConstructor
public class DeviationTrendPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "parameter_name", nullable = false, length = 100)
    private String parameterName;

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    /**
     * Converted automatically by TrendDirectionPersistenceConverter.
     */
    @Column(name = "trend_direction", nullable = false, length = 50)
    private TrendDirection trendDirection;
}