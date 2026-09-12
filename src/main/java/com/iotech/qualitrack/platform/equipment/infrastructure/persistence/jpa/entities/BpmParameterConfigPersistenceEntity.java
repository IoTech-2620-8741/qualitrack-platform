package com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a BPM Parameter Configuration table.
 */
@Entity
@Table(name = "bpm_parameter_configs")
@Getter
@Setter
@NoArgsConstructor
public class BpmParameterConfigPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    /**
     * Mapeado como String puro. El Assembler lo convertirá a CriticalVariable en el dominio.
     */
    @Column(name = "parameter_name", nullable = false, length = 100)
    private String parameterName;

    @Column(name = "min_value", nullable = false)
    private Double minValue;

    @Column(name = "max_value", nullable = false)
    private Double maxValue;

    @Column(nullable = false, length = 20)
    private String unit;
}