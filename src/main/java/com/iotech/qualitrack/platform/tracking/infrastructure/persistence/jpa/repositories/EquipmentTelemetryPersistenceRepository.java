package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.EquipmentTelemetryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for equipment telemetry aggregate persistence entities.
 */
public interface EquipmentTelemetryPersistenceRepository
        extends JpaRepository<EquipmentTelemetryPersistenceEntity, Long> {
    /**
     * Finds an equipment telemetry aggregate by equipment identifier.
     *
     * @param equipmentId the equipment identifier
     * @return the aggregate persistence entity when found
     */
    Optional<EquipmentTelemetryPersistenceEntity> findByEquipmentId(Long equipmentId);

    /**
     * Verifies whether an aggregate exists for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return true when the aggregate exists
     */
    boolean existsByEquipmentId(Long equipmentId);
}