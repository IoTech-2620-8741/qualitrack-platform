package com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing an Equipment table.
 *
 * <p>This class bridges the relational database schema with the application's
 * data access layer. It reflects the structure defined by the Angular frontend,
 * integrating IoT tracking and operational status.</p>
 */
@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
public class EquipmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    // El ID numérico (Long) se hereda de AuditableAbstractPersistenceEntity.

    @Column(name = "laboratory_id", nullable = false)
    private Long labId;

    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Mapeado como String puro. El Assembler lo convertirá a EquipmentType en el dominio.
     */
    @Column(nullable = false, length = 100)
    private String type;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(name = "serial_number", nullable = false, length = 50, unique = true)
    private String serialNumber;

    /**
     * Convertido automáticamente por EquipmentStatusPersistenceConverter (Enum).
     */
    @Column(nullable = false, length = 50)
    private EquipmentStatus status;

    /**
     * Mapeado como String puro. El Assembler lo convertirá a DeviceId en el dominio.
     */
    @Column(name = "sensor_external_id", length = 50)
    private String sensorExternalId;
}